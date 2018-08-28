package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.PenicillinStreamResponse
import kotlinx.coroutines.experimental.io.jvm.javaio.toInputStream
import java.io.Closeable
import java.io.IOException
import java.util.concurrent.TimeUnit

class StreamProcessor<L: StreamListener, H: StreamHandler<L>>(private var response: PenicillinStreamResponse<L, H>, private val handler: H): Closeable {
    private var shouldStop = false

    fun start(wait: Boolean = false, autoReconnect: Boolean = true) = apply {
        handler.executor.execute {
            while (!shouldStop) {
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
            handler.listener.onConnect()

            while (!shouldStop) {
                val line = try {
                    (it.readLine() ?: continue).trim().unescapeHTML()
                } catch (e: IOException) {
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

            handler.listener.onDisconnect()
        }
    }
}

internal fun String.unescapeHTML(): String {
    return replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
}
