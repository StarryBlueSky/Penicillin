package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonKt
import jp.nephy.penicillin.exception.TwitterAPIError
import jp.nephy.penicillin.misc.unescapeHTMLCharacters
import jp.nephy.penicillin.response.ResponseStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.SocketException
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

abstract class StreamingParser<T>(response: ResponseStream<T>) {
    private val stream = response.response.body()!!.byteStream()
    private val buffer = BufferedReader(InputStreamReader(stream))
    private var stop = false

    private var onClose: () -> Unit = {}

    fun start(): StreamingParser<T> {
        thread(name="readline daemon") {
            readLine { json ->  handle(json) }
        }
        return this
    }
    fun terminate() {
        stop = true
    }

    fun onClose(callable: () -> Unit): StreamingParser<T> {
        return this.apply {
            onClose = callable
        }
    }

    protected abstract fun handle(json: JsonObject)

    private fun readLine(callback: (JsonObject) -> Unit) {
        while (! stop) {
            val line = try {
                buffer.readLine() ?: break
            } catch (e: SocketException) {
                break
            } catch (e: SocketTimeoutException) {
                break
            }

            if (line.isNotEmpty()) {
                if (! line.startsWith("{")) {
                    throw TwitterAPIError("Stream got illigal character.", line)
                }

                thread(name="callback", isDaemon=false) {
                    callback(JsonKt.toJsonObject(
                            line.unescapeHTMLCharacters()
                    ))
                }
            }
        }

        buffer.close()
        stream.close()
        onClose()
    }
}
