package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString

data class UserRetweet(override val json: JsonObject): PenicillinModel {
    val id by json.byLong
    val idStr by json.byString("id_str")
}
