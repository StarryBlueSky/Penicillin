package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class SampleStreamHandler(override val listener: SampleStreamListener): StreamHandler<SampleStreamListener> {
    override suspend fun handle(json: JsonObject, context: CoroutineContext) {
        when {
            json.contains("text") -> launch(context) {
                listener.onStatus(Status(json))
            }
            json.contains("delete") -> launch(context) {
                listener.onDelete(StreamDelete(json))
            }
            else -> launch(context) {
                listener.onUnhandledJson(json)
            }
        }

        launch(context) {
            listener.onAnyJson(json)
        }
    }
}

interface SampleStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: StreamDelete) {}
}
