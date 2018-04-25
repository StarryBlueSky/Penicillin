package jp.nephy.penicillin.request.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.model.StreamDelete
import jp.nephy.penicillin.request.StreamAction

typealias SampleStreamListener = SampleStream.Listener

class SampleStream(action: StreamAction<SampleStreamListener>, listener: SampleStreamListener): StreamHandler<SampleStreamListener>(action, listener) {
    override fun handle(json: JsonObject) = when {
        json.contains("text") -> listener.onStatus(Status(json))
        json.contains("delete") -> listener.onDelete(StreamDelete(json))
        else -> listener.onUnknownData(json)
    }

    interface Listener: StreamListener {
        fun onStatus(status: Status) {}
        fun onDelete(delete: StreamDelete) {}
        fun onUnknownData(data: JsonObject) {}
    }
}
