package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import jp.nephy.penicillin.core.unescapeHTML
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import mu.KotlinLogging
import java.io.Closeable
import java.io.IOException

private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var response: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
    private var shouldStop = false

    fun start(wait: Boolean = false, autoReconnect: Boolean = true) = apply {
        if (wait) {
            runBlocking {
                loop(autoReconnect)
            }
        } else {
            launch {
                loop(autoReconnect)
            }
        }
    }

    override fun close() {
        shouldStop = true
    }

    private suspend fun loop(autoReconnect: Boolean) {
        while (!shouldStop) {
            block()

            if (!autoReconnect) {
                break
            }

            response = response.action.request.stream<L, H>().await()
        }
    }

    private suspend fun block() {
        response.response.content.toInputStream().bufferedReader().use { reader ->
            logger.info { "Connected to ${response.request.url}." }
            handler.listener.onConnect()

            while (!shouldStop) {
                val line = try {
                    (reader.readLine() ?: continue).trim().unescapeHTML()
                } catch (e: IOException) {
                    logger.debug { "IOException caused while readLine. (${response.request.url})" }
                    close()
                    break
                }

                if (line.isBlank()) {
                    continue
                }

                launch {
                    handler.listener.onRawData(line)
                }
                launch {
                    line.toJsonObject().let { handler.handle(it) }
                }
            }

            logger.info { "Disconnected from ${response.request.url}." }
            handler.listener.onDisconnect()
        }
    }
}
