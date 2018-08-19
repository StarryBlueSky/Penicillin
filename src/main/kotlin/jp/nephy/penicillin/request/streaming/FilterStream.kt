package jp.nephy.penicillin.request.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.model.StreamDelete
import jp.nephy.penicillin.request.StreamAction

typealias FilterStreamListener = FilterStream.Listener

class FilterStream(action: StreamAction<FilterStreamListener>, listener: FilterStreamListener): StreamHandler<FilterStreamListener>(action, listener) {
    override fun handle(json: JsonObject) {
        executor.execute { listener.onAnyData(json) }
        when {
            json.contains("text") -> listener.onStatus(Status(json))
            json.contains("delete") -> listener.onDelete(StreamDelete(json))
            else -> listener.onUnknownData(json)
        }
    }

    interface Listener: StreamListener {
        fun onStatus(status: Status) {}
        fun onDelete(delete: StreamDelete) {}
        fun onUnknownData(data: JsonObject) {}
    }
}
