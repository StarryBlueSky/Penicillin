package jp.nephy.penicillin.core.streaming

import io.ktor.util.flattenEntries
import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import jp.nephy.penicillin.core.unescapeHTML
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream
import mu.KotlinLogging
import java.io.Closeable
import java.io.IOException
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger("Penicillin.StreamProcessor")

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var response: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
    private var shouldStop = false

    fun start(wait: Boolean = false, autoReconnect: Boolean = true) = apply {
        handler.executor.execute {
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

                response = response.action.request.stream<L, H>().complete()
            }
        }

        while (wait && !shouldStop) {
            TimeUnit.SECONDS.sleep(2)
        }
    }

    fun terminate() {
        close()
    }

    override fun close() {
        shouldStop = true
    }

    private fun block() {
        response.response.content.toInputStream().bufferedReader().use {
            logger.info { "Connected to ${response.request.url}." }
            handler.listener.onConnect()

            while (!shouldStop) {
                val line = try {
                    (it.readLine() ?: continue).trim().unescapeHTML()
                } catch (e: IOException) {
                    logger.debug { "IOException caused while readLine. (${response.request.url})" }
                    terminate()
                    break
                }

                if (line.isBlank()) {
                    continue
                }

                handler.executor.execute { handler.listener.onRawData(line) }
                handler.executor.execute {
                    val json = try {
                        line.toJsonObject()
                    } catch (e: Exception) {
                        return@execute
                    }

                    handler.handle(json)
                }
            }

            logger.info { "Disconnected from ${response.request.url}." }
            handler.listener.onDisconnect()
        }
    }
}
