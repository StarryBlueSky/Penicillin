package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byModel
import jp.nephy.penicillin.misc.CreatedAt

class UserEvent(val json: JsonElement) {
    val event by json.byString
    val source by json.byModel<User>()
    val target by json.byModel<User>()
    val createdAt by json.byConverter<String, CreatedAt>("created_at")
}