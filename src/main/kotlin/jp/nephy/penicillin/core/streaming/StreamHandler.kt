package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: JsonObject)
}
