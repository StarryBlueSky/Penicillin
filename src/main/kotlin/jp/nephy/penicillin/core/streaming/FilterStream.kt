package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class FilterStreamHandler(override val listener: FilterStreamListener): StreamHandler<FilterStreamListener> {
    override suspend fun handle(json: JsonObject, context: CoroutineContext) {
        when {
            json.contains("text") -> launch(context) {
                listener.onStatus(Status(json))
            }
            json.contains("delete") -> launch(context) {
                listener.onDelete(StreamDelete(json))
            }
            else -> launch(context) {
                listener.onUnhandledData(json)
            }
        }

        launch(context) {
            listener.onRawJson(json)
        }
    }
}

interface FilterStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: StreamDelete) {}
}
