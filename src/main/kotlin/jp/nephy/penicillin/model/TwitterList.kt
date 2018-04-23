package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.util.CreatedAt


class TwitterList(override val json: JsonObject): JsonModel {
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
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
    val user by json.byModel<User>()
}
