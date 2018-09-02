package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byString

class Webhook(override val json: JsonObject): PenicillinModel {
    val id by json.byString
    val url by json.byString
    val valid by json.byBool
    val createdAt by json.byString("created_at")
}
