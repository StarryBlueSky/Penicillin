package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject

interface StreamListener {
    suspend fun onConnect() {}
    suspend fun onDisconnect() {}
    suspend fun onHeartbeat() {}
    suspend fun onRawJson(json: JsonObject) {}
    suspend fun onRawData(data: String) {}
    suspend fun onUnhandledData(data: JsonObject) {}
}
