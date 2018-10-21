package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.ImmutableJsonObject

interface StreamListener {
    suspend fun onConnect() {}
    suspend fun onDisconnect() {}

    suspend fun onHeartbeat() {}
    suspend fun onLength(length: Int) {}

    suspend fun onAnyJson(json: ImmutableJsonObject) {}
    suspend fun onRawData(data: String) {}
    suspend fun onUnhandledJson(json: ImmutableJsonObject) {}
    suspend fun onUnknownData(data: String) {}
}
