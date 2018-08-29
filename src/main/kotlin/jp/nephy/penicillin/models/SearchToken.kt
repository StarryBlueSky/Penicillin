package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString

data class SearchToken(override val json: JsonObject): PenicillinModel {
    val token by json.byString
}
