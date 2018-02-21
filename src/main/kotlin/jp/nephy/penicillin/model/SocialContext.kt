package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byBool

@Suppress("UNUSED")
class SocialContext(override val json: JsonObject): JsonModel {
    val following by json.byBool
    val followedBy by json.byBool("followed_by")
}
