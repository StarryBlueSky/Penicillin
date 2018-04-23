package jp.nephy.penicillin.streaming

interface StreamListener<T: StreamHandler> {
    fun onConnect() {}
    fun onDisconnect() {}
}
