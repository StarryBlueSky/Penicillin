package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byErrorArray

class ErrorResponse(val json: JsonElement) {
    val errors by json.byErrorArray
}
