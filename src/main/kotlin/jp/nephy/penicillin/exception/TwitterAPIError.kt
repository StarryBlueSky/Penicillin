package jp.nephy.penicillin.exception

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.model.ErrorResponse

class TwitterAPIError(msg: String, val content: String): Exception() {
    override var message = "$msg\n`$content` returned."
    var json: ErrorResponse = ErrorResponse(Gson().fromJson(content, JsonObject::class.java))
}
