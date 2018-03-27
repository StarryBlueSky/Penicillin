package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status
import jp.nephy.penicillin.response.ResponseStream

class FilterStreamReceiver(response: ResponseStream<IFilterStreamListener>, private val listener: IFilterStreamListener): StreamingParser<IFilterStreamListener>(response) {
    override fun handle(json: JsonObject) = when {
        json.has("text") -> listener.onStatus(Status(json))
        json.has("delete") -> listener.onDelete(Delete(json))
        else -> listener.onUnknownData(json.toString())
    }
}
