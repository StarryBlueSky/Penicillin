package jp.nephy.penicillin.request.streaming

import com.google.gson.JsonObject

interface StreamListener {
    fun onConnect() {}
    fun onDisconnect() {}
    fun onAnyData(json: JsonObject) {}
    fun onRawData(data: String) {}
}
