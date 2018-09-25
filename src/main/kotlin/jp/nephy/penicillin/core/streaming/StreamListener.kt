package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject

interface StreamListener {
    suspend fun onConnect() {}
    suspend fun onDisconnect() {}

    suspend fun onHeartbeat() {}
    suspend fun onLength(length: Int) {}

    suspend fun onAnyJson(json: JsonObject) {}
    suspend fun onRawData(data: String) {}
    suspend fun onUnhandledJson(json: JsonObject) {}
    suspend fun onUnknownData(data: String) {}
}
