package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool


data class SocialContext(override val json: JsonObject): PenicillinModel {
    val following by json.byBool
    val followedBy by json.byBool("followed_by")
}
