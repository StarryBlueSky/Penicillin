package jp.nephy.penicillin.exception

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.model.ErrorResponse

class TwitterAPIError(msg: String, val content: String, val code: Int?=null, message: String?=null): Exception() {
    override var message = message ?: "$msg\n`$content` returned."
}
