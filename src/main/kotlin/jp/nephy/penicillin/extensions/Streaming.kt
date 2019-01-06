package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.streaming.StreamProcessor
import jp.nephy.penicillin.core.streaming.handler.StreamHandler
import jp.nephy.penicillin.core.streaming.listener.StreamListener
import kotlinx.coroutines.runBlocking

fun <L: StreamListener, H: StreamHandler<L>> StreamProcessor<L, H>.startBlocking(autoReconnect: Boolean = true): StreamProcessor<L, H> {
    return apply {
        runBlocking {
            startAsync(autoReconnect)
            await()
        }
    }
}
