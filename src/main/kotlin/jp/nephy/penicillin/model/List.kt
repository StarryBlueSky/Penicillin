package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byCreatedAt
import jp.nephy.penicillin.converter.byUser

class List(val json: JsonElement) {
    val createdAt by json.byCreatedAt("created_at")
    val description by json.byString
    val following by json.byBool
    val fullName by json.byString("full_name")
    val id by json.byLong
    val idStr by json.byString("id_str")
    val memberCount by json.byInt("member_count")
    val mode by json.byString
    val name by json.byString
    val slug by json.byString
    val subscriberCount by json.byInt("subscriber_count")
    val uri by json.byString
    val user by json.byUser
}
