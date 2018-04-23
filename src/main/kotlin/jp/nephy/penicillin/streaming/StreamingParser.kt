package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonKt
import jp.nephy.penicillin.util.unescapeHTMLCharacters
import okhttp3.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

abstract class StreamingParser(response: Response, private val listener: StreamListener<*>): StreamHandler {
    private val source = response.body()!!.source()
    private val stream = source.inputStream()
    private val buffer = BufferedReader(InputStreamReader(stream))
    private var stop = false

    val isEndStream: Boolean
        get() = try {
            source.exhausted()
        } catch (e: Exception) {
            false
        }

    fun start(wait: Boolean = false) = apply {
        thread {
            readLine { json -> handle(json) }
        }

        while (wait && ! stop) {
            Thread.sleep(1000)
        }
    }

    fun terminate() {
        stop = true
    }

    private fun readLine(callback: (JsonObject) -> Unit) {
        listener.onConnect()
        while (true) {
            if (stop || isEndStream) {
                break
            }

            val line = try {
                buffer.readLine()
            } catch (e: Exception) {
                null
            } ?: return

            if (line.isBlank()) {
                continue
            }

            thread {
                val json = try {
                    JsonKt.toJsonObject(line.unescapeHTMLCharacters())
                } catch (e: Exception) {
                    return@thread
                }

                callback(json)
            }
        }

        stop = true
        buffer.close()
        stream.close()
        listener.onDisconnect()
    }
}
