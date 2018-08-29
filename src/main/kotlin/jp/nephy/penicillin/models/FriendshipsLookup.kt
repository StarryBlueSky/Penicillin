package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byStringList


data class FriendshipsLookup(override val json: JsonObject): PenicillinModel {
    val connections by json.byStringList
    val id by json.byLong
    val idStr by json.byString("id_str")
    val name by json.byString
    val screenName by json.byString("screen_name")
}
