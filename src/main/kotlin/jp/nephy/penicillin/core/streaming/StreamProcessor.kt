package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import jp.nephy.penicillin.core.unescapeHTML
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream
import java.io.Closeable
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private val response: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
    private val job = Job()

    fun start(wait: Boolean = false, autoReconnect: Boolean = true, context: CoroutineContext = DefaultDispatcher) = apply {
        if (wait) {
            runBlocking(context) {
                loop(autoReconnect, context)
            }
        } else {
            launch(context, parent = job) {
                loop(autoReconnect, context)
            }
        }

        close()
    }

    override fun close() {
        runBlocking(CommonPool) {
            job.cancelChildren()
            job.cancelAndJoin()
        }
    }

    private suspend fun CoroutineScope.loop(autoReconnect: Boolean, context: CoroutineContext) {
        var streamResponse = response
        while (isActive) {
            try {
                block(streamResponse, context)

                if (!autoReconnect) {
                    return
                }

                while (isActive) {
                    try {
                        streamResponse = response.action.request.stream<L, H>().await()
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

    private suspend fun CoroutineScope.block(response: PenicillinStreamResponse<L, H>, context: CoroutineContext) {
        response.response.content.toInputStream(parent = job).bufferedReader().use { reader ->
            launch(context) {
                handler.listener.onConnect()
            }

            loop@ while (isActive) {
                try {
                    val line = reader.readLine() ?: break
                    when {
                        line.isBlank() -> {
                            launch(context) {
                                handler.listener.onHeartbeat()
                            }

                            continue@loop
                        }
                        line.startsWith("{") -> {
                            val content = line.trim().unescapeHTML()

                            launch(context) {
                                handler.listener.onRawData(content)
                            }
                            launch(context) {
                                handler.handle(content.toJsonObject(), context)
                            }
                        }
                        line.toIntOrNull() != null -> {
                            launch(context) {
                                handler.listener.onLength(line.toInt())
                            }
                        }
                        else -> {
                            launch(context) {
                                handler.listener.onUnknownData(line)
                            }
                        }
                    }
                } catch (e: CancellationException) {
                    break
                } catch (e: IOException) {
                    break
                }
            }

            launch(context) {
                handler.listener.onDisconnect()
            }
        }
    }
}
