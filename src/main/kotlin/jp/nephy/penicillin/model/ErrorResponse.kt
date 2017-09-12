package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byErrorArray

class ErrorResponse(val json: JsonElement) {
    val errors by json.byErrorArray
}
