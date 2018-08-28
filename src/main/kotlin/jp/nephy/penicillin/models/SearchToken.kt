package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byString

class SearchToken(override val json: JsonObject): PenicillinModel {
    val token by json.byString
}
