package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byIntArray

class UserMentionEntity(val json: JsonElement) {
    val screenName by json.byString("screen_name")
    val name by json.byString
    val id by json.byLong
    val idStr by json.byString("id_str")
    val indices by json.byIntArray
}
