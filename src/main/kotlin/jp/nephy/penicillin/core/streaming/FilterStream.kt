package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import kotlinx.coroutines.experimental.launch
import kotlin.coroutines.experimental.CoroutineContext

class FilterStreamHandler(override val listener: FilterStreamListener): StreamHandler<FilterStreamListener> {
    override suspend fun handle(json: JsonObject, context: CoroutineContext) {
        launch(context) {
            when {
                json.contains("text") -> {
                    listener.onStatus(Status(json))
                }
                json.contains("delete") -> {
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

interface FilterStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: StreamDelete) {}
}
