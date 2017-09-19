package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class Friends(val json: JsonElement) {
    val friends by json.byList<Long>()
}
