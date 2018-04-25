package jp.nephy.penicillin.request.streaming

interface StreamListener {
    fun onConnect() {}
    fun onDisconnect() {}
}
