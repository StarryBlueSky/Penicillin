package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Guest(override val json: JsonObject): JsonModel {
    val guestToken by json.byString("guest_token")
}
