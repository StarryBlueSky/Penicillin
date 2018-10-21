package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.ImmutableJsonObject
import kotlin.coroutines.experimental.CoroutineContext

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: ImmutableJsonObject, context: CoroutineContext)
}
