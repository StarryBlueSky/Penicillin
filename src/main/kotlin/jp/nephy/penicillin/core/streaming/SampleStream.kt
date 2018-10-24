package jp.nephy.penicillin.core.streaming

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SampleStreamHandler(override val listener: SampleStreamListener): StreamHandler<SampleStreamListener> {
    override suspend fun handle(json: ImmutableJsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            when {
                "text" in json -> {
                    listener.onStatus(Status(json))
                }
                "delete" in json -> {
                    listener.onDelete(StreamDelete(json))
                }
                else -> {
                    listener.onUnhandledJson(json)
                }
            }
        }

        listener.onAnyJson(json)
    }
}

interface SampleStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: StreamDelete) {}
}
