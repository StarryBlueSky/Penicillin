package jp.nephy.penicillin.request.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonKt
import jp.nephy.penicillin.request.StreamAction
import jp.nephy.penicillin.unescapeHTMLCharacters
import okio.BufferedSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Executors
import kotlin.concurrent.thread

abstract class StreamHandler<T: StreamListener>(private var action: StreamAction<T>, val listener: T) {
    val executor = Executors.newCachedThreadPool()
    private val source: BufferedSource
        get() = action.okHttpResponse.body()!!.source()
    private var stop = false

    val isEndStream: Boolean
        get() = try {
            source.exhausted()
        } catch (e: Exception) {
            false
        }

    abstract fun handle(json: JsonObject)

    fun start(wait: Boolean = false, autoReconnect: Boolean = true) = apply {
        thread {
            while (! stop) {
                readLine { json -> handle(json) }
                if (! autoReconnect) {
                    break
                }
                action = StreamAction(action.requestBuilder)
            }
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

        source.inputStream().bufferedReader().use {
            while (true) {
                if (stop || isEndStream) {
                    break
                }

                val line = try {
                    it.readLine()
                } catch (e: Exception) {
                    null
                } ?: return

                if (line.isBlank()) {
                    continue
                }

                executor.execute { listener.onRawData(line) }
                executor.execute {
                    val json = try {
                        JsonKt.toJsonObject(line.trim().unescapeHTMLCharacters())
                    } catch (e: Exception) {
                        return@execute
                    }

                    callback(json)
                }
            }

            stop = true
            listener.onDisconnect()
        }
    }
}
