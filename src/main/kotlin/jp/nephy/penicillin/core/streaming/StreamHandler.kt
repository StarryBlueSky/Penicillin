package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import java.util.concurrent.ExecutorService

interface StreamHandler<L: StreamListener> {
    val listener: L
    val executor: ExecutorService

    fun handle(json: JsonObject)
}
