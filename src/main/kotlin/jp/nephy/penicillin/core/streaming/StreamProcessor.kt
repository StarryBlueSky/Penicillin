package jp.nephy.penicillin.core.streaming

import io.ktor.util.flattenEntries
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

    fun terminate() {
        close()
    }

    override fun close() {
        shouldStop = true
    }

    private suspend fun loop(autoReconnect: Boolean) {
        while (!shouldStop) {
            logger.trace {
                buildString {
                    appendln("${response.response.version} ${response.response.status.value} ${response.request.method.value} ${response.request.url}")

                    val (requestHeaders, responseHeaders) = response.request.headers.flattenEntries() to response.response.headers.flattenEntries()
                    val (longestRequestHeaderLength, longestResponseHeaderLength) = requestHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1 to responseHeaders.maxBy { it.first.length }?.first.orEmpty().length + 1
                    appendln("Request headers =\n${requestHeaders.joinToString("\n") { "    ${it.first.padEnd(longestRequestHeaderLength)}: ${it.second}" }}")
                    append("Response headers =\n${responseHeaders.joinToString("\n") { "    ${it.first.padEnd(longestResponseHeaderLength)}: ${it.second}" }}")
                }
            }

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
                    terminate()
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
