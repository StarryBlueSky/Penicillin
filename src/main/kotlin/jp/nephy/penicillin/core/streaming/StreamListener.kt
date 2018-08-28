package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject

interface StreamListener {
    fun onConnect() {}
    fun onDisconnect() {}
    fun onRawJson(json: JsonObject) {}
    fun onRawData(data: String) {}
    fun onUnhandledData(data: JsonObject) {}
}
