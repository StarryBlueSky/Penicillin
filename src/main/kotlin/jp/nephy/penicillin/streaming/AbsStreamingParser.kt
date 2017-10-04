package jp.nephy.penicillin.streaming

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.exception.TwitterAPIError
import jp.nephy.penicillin.response.ResponseStream
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

abstract class AbsStreamingParser(response: ResponseStream) {
    private val gson = Gson()
    private val stream = response.response.body()!!.byteStream()
    private val buffer = BufferedReader(InputStreamReader(stream))
    private var stop = false

    private var onClose: () -> Unit = {}

    fun start(): AbsStreamingParser {
        thread(name="readline daemon") {
            readLine { json ->  handle(json) }
        }
        return this
    }
    fun terminate() {
        stop = true
    }

    fun onClose(callable: () -> Unit): AbsStreamingParser {
        return this.apply {
            onClose = callable
        }
    }

    protected abstract fun handle(json: JsonObject)

    private fun readLine(callback: (JsonObject) -> Unit) {
        while (! stop) {
            val line = buffer.readLine() ?: break

            if (line.isNotEmpty()) {
                if (! line.startsWith("{")) {
                    throw TwitterAPIError("Stream get illigal character.", line)
                }

                thread(name="callback", isDaemon=false) {
                    callback(gson.fromJson(
                            line.replace("&amp;", "&")
                                .replace("&lt;", "<")
                                .replace("&gt;", ">"),
                            JsonObject::class.java
                    ))
                }
            }
        }

        buffer.close()
        stream.close()
        onClose()
    }
}
