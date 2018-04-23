package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject
import jp.nephy.jsonkt.contains
import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status
import okhttp3.Response

class FilterStreamHandler(response: Response, private val listener: FilterStreamListener): StreamingParser(response, listener) {
    override fun handle(json: JsonObject) = when {
        json.contains("text") -> listener.onStatus(Status(json))
        json.contains("delete") -> listener.onDelete(Delete(json))
        else -> listener.onUnknownData(json.toString())
    }
}
