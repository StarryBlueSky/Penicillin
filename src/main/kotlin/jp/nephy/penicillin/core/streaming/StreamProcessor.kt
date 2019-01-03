@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.request.action.unescapeHTML
import jp.nephy.penicillin.core.response.StreamResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.io.readUTF8Line
import java.io.Closeable

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var result: StreamResponse<L, H>, private val handler: H): Closeable {
    private val job = Job()

    fun startBlocking(autoReconnect: Boolean = true) = apply {
        use {
            runBlocking(result.action.session.coroutineContext + job) {
                loop(autoReconnect)
            }
        }
    }

    fun startAsync(autoReconnect: Boolean = true) = apply {
        result.action.session.launch(job) {
            loop(autoReconnect)
            close()
        }
    }

    override fun close() {
        runBlocking(Dispatchers.Default) {
            job.cancelChildren()
            job.cancelAndJoin()
        }
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
                        result = result.action.request.stream<L, H>().await()
                        break
                    } catch (e: CancellationException) {
                        return
                    } catch (e: Throwable) {
                        delay(1000)
                        continue
                    }
                }
            } catch (e: CancellationException) {
                return
            }
        }
    }

    private suspend fun CoroutineScope.process() {
        launch(coroutineContext) {
            handler.listener.onConnect()
        }

        // TODO: investigate streaming delays
        while (isActive && !result.response.content.isClosedForRead) {
            val line = try {
                result.response.content.readUTF8Line()?.trim() ?: break
            } catch (e: Throwable) {
                break
            }

            launch(coroutineContext) {
                when {
                    line.startsWith("{") -> {
                        val content = line.unescapeHTML()

                        launch(coroutineContext) {
                            handler.listener.onRawData(content)
                        }
                        handler.handle(content.toJsonObject(), this)
                    }
                    line.isBlank() -> {
                        handler.listener.onHeartbeat()
                    }
                    else -> {
                        val length = line.toIntOrNull() ?: return@launch handler.listener.onUnknownData(line)
                        handler.listener.onLength(length)
                    }
                }
            }
        }

        launch(coroutineContext) {
            handler.listener.onDisconnect()
        }
    }
}
