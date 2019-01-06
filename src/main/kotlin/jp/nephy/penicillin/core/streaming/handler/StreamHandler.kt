package jp.nephy.penicillin.core.streaming.handler

import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import kotlinx.coroutines.CoroutineScope

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: JsonObject, scope: CoroutineScope)
}
