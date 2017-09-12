package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byStringArray

class FriendshipsLookup(val json: JsonElement) {
    val connections by json.byStringArray
    val id by json.byLong
    val idStr by json.byString("id_str")
    val name by json.byString
    val screenName by json.byString("screen_name")
}
