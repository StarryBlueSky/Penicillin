package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.JsonObject
import kotlin.coroutines.experimental.CoroutineContext

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: JsonObject, context: CoroutineContext)
}
