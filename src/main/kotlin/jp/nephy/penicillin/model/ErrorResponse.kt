package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class ErrorResponse(val json: JsonElement) {
    val errors by json.byList<Error>()
}
