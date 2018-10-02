package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import jp.nephy.penicillin.core.unescapeHTML
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.io.readUTF8Line
import java.io.Closeable
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var result: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
    private val job = Job()

    fun start(wait: Boolean = false, autoReconnect: Boolean = true, context: CoroutineContext? = null) = apply {
        if (wait) {
            runBlocking(context ?: EmptyCoroutineContext) {
                loop(autoReconnect, context ?: EmptyCoroutineContext)
                close()
            }
        } else {
            launch(context ?: DefaultDispatcher, parent = job) {
                loop(autoReconnect, context ?: DefaultDispatcher)
                close()
            }
        }
    }

    override fun close() {
        runBlocking(CommonPool) {
            job.cancelChildren()
            job.cancelAndJoin()
        }
    }

    private suspend fun CoroutineScope.loop(autoReconnect: Boolean, context: CoroutineContext) {
        while (isActive) {
            try {
                process(context)

                if (!autoReconnect) {
                    return
                }

                while (isActive) {
                    try {
                        result = result.action.request.stream<L, H>().await()
                        break
                    } catch (e: CancellationException) {
                        return
                    } catch (e: Exception) {
                        delay(1, TimeUnit.SECONDS)
                        continue
                    }
                }
            } catch (e: CancellationException) {
                return
            }
        }
    }

    private suspend fun CoroutineScope.process(context: CoroutineContext) {
        launch(context) {
            handler.listener.onConnect()
        }

        while (isActive && !result.response.content.isClosedForRead) {
            val line = try {
                result.response.content.readUTF8Line()?.trim() ?: break
            } catch (e: Exception) {
                break
            }

            launch(context) {
                when {
                    line.startsWith("{") -> {
                        val content = line.unescapeHTML()

                        launch(context) {
                            handler.listener.onRawData(content)
                        }
                        handler.handle(content.toJsonObject(), context)
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

        launch(context) {
            handler.listener.onDisconnect()
        }
    }
}
