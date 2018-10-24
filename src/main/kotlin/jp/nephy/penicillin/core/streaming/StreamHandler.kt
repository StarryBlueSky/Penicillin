package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.ImmutableJsonObject
import kotlinx.coroutines.CoroutineScope

interface StreamHandler<L: StreamListener> {
    val listener: L

    suspend fun handle(json: ImmutableJsonObject, scope: CoroutineScope)
}
