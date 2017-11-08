package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byLong
import com.google.gson.JsonElement

@Suppress("UNUSED")
class ActivityAboutMeUnread(val json: JsonElement) {
    val cursor by json.byLong
}
