package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byLongArray

class Friends(val json: JsonElement) {
    val friends by json.byLongArray
}
