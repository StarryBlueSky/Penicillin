package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.google.gson.JsonElement

@Suppress("UNUSED")
class SocialContext(val json: JsonElement) {
    val following by json.byBool
    val followedBy by json.byBool("followed_by")
}
