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

    init {
        thread(name="readline daemon") {
            readLine { json ->  handle(json) }
        }
    }

    fun terminate() {
        buffer.close()
        stream.close()

        stop = true
    }

    private fun unescape(line: String): String {
        return line.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
    }

    protected abstract fun handle(json: JsonObject)

    private fun readLine(callback: (JsonObject) -> Unit) {
        while (true) {
            if (stop) {
                break
            }

            val line = buffer.readLine()
            if (line != null && line.isNotEmpty()) {
                if (! line.startsWith("{")) {
                    throw TwitterAPIError("Stream get illigal character.", line)
                }

                thread(name="callback", isDaemon=false) {
                    callback(gson.fromJson(unescape(line), JsonObject::class.java))
                }
            }
        }
    }
}
