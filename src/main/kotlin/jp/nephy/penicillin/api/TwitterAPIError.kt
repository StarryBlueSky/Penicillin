package jp.nephy.penicillin.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.api.model.ErrorResponse

class TwitterAPIError(msg: String, val content: String): Exception() {
    override var message = "$msg\n`$content` returned."
    var json: ErrorResponse = ErrorResponse(Gson().fromJson(content, JsonObject::class.java))
}
