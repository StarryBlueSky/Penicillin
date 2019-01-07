@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.i18n.LocalizedString
import jp.nephy.penicillin.core.request.action.unescapeHTML
import jp.nephy.penicillin.core.response.StreamResponse
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import kotlinx.coroutines.*
import kotlinx.coroutines.io.jvm.javaio.toInputStream
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.io.core.Closeable
import mu.KotlinLogging

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var result: StreamResponse<L, H>, private val handler: H): Closeable {
    private val job = Job()
    private val mutex = Mutex()
    private object Dummy

    private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")

    suspend fun await() {
        mutex.lock()
    }

    suspend fun startAsync(autoReconnect: Boolean = true): StreamProcessor<L, H> {
        return mutex.withLock(Dummy) {
            apply {
                result.action.session.launch(result.action.session.coroutineContext + job) {
                    use {
                        loop(autoReconnect)
                    }
                }
            }
        }
    }

    override fun close() {
        result.close()
        job.cancelChildren()
    }

    private suspend fun CoroutineScope.loop(autoReconnect: Boolean) {
        while (isActive) {
            try {
                process()

                if (!autoReconnect) {
                    return
                }

                while (isActive) {
                    try {
                        result.close()
                        result = result.action.request.stream<L, H>().await()
                        break
                    } catch (e: CancellationException) {
                        return
                    } catch (e: Throwable) {
                        logger.error(e) { LocalizedString.ExceptionInAsyncBlock }

                        delay(result.action.session.option.retryInMillis)
                        continue
                    }
                }
            } catch (e: CancellationException) {
                return
            } catch (e: Throwable) {
                logger.error(e) { LocalizedString.ExceptionInAsyncBlock }
            }
        }
    }

    private suspend fun CoroutineScope.process() {
        launch {
            handler.listener.onConnect()
        }

        result.response.content.toInputStream(job).bufferedReader().use { reader ->
            for (line in reader.lineSequence()) {
                if (!isActive) {
                    break
                }

                // TODO: investigate streaming delays
                val content = line.trim().unescapeHTML()

                launch {
                    handler.listener.onRawData(content)
                }

                launch {
                    when {
                        content.startsWith("{") -> {
                            handler.handle(content.toJsonObject(), this)
                        }
                        content.isBlank() -> {
                            handler.listener.onHeartbeat()
                        }
                        else -> {
                            val length = content.toIntOrNull() ?: return@launch handler.listener.onUnknownData(content)
                            handler.listener.onLength(length)
                        }
                    }
                }
            }
        }

        launch {
            handler.listener.onDisconnect()
        }
    }
}
