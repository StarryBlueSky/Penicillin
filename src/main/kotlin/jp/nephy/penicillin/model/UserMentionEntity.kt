package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString


class UserMentionEntity(override val json: JsonObject): JsonModel {
    val screenName by json.byString("screen_name")
    val name by json.byString
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byIntList()
}
