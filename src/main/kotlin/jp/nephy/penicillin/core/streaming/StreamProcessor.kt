package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import jp.nephy.penicillin.core.unescapeHTML
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream
import mu.KotlinLogging
import java.io.Closeable
import java.io.IOException
import kotlin.coroutines.experimental.CoroutineContext

private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var response: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
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
    }

    override fun close() {
        runBlocking(CommonPool) {
            job.cancelChildren()
            job.cancelAndJoin()
        }
    }

    private suspend fun CoroutineScope.loop(autoReconnect: Boolean, context: CoroutineContext) {
        while (isActive) {
            block(context)

            if (!autoReconnect) {
                break
            }

            response = response.action.request.stream<L, H>().await()
        }
    }

    private suspend fun CoroutineScope.block(context: CoroutineContext) {
        response.response.content.toInputStream().bufferedReader().use { reader ->
            launch(context) {
                handler.listener.onConnect()
            }

            while (isActive) {
                val line = try {
                    (reader.readLine() ?: continue).trim().unescapeHTML()
                } catch (e: IOException) {
                    logger.debug { "IOException caused while readLine. (${response.request.url})" }
                    close()
                    break
                }

                if (line.isBlank()) {
                    launch(context) {
                        handler.listener.onHeartbeat()
                    }
                    continue
                }

                launch(context) {
                    handler.listener.onRawData(line)
                }
                launch(context) {
                    line.toJsonObject().let { handler.handle(it, context) }
                }
            }

            launch(context) {
                handler.listener.onDisconnect()
            }
        }
    }
}
