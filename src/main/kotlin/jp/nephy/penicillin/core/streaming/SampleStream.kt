package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import kotlinx.coroutines.experimental.launch

class SampleStreamHandler(override val listener: SampleStreamListener): StreamHandler<SampleStreamListener> {
    override suspend fun handle(json: JsonObject) {
        launch { listener.onRawJson(json) }
        when {
            json.contains("text") -> listener.onStatus(Status(json))
            json.contains("delete") -> listener.onDelete(StreamDelete(json))
            else -> listener.onUnhandledData(json)
        }
    }
}

interface SampleStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: StreamDelete) {}
}
