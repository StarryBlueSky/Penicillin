package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.JsonObject
import kotlinx.coroutines.CoroutineScope

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: JsonObject, scope: CoroutineScope)
}
