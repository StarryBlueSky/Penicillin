package jp.nephy.penicillin.core.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.StreamDelete
import java.util.concurrent.ExecutorService

class SampleStreamHandler(override val listener: SampleStreamListener, override val executor: ExecutorService): StreamHandler<SampleStreamListener> {
    override fun handle(json: JsonObject) {
        executor.execute { listener.onRawJson(json) }
        when {
            json.contains("text") -> listener.onStatus(Status(json))
            json.contains("delete") -> listener.onDelete(StreamDelete(json))
            else -> listener.onUnhandledData(json)
        }
    }
}

interface SampleStreamListener: StreamListener {
    fun onStatus(status: Status) {}
    fun onDelete(delete: StreamDelete) {}
}
