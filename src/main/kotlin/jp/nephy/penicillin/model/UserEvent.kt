package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byCreatedAt
import jp.nephy.penicillin.converter.byUser

class UserEvent(val json: JsonElement) {
    val event by json.byString
    val source by json.byUser
    val target by json.byUser
    val createdAt by json.byCreatedAt("created_at")
}