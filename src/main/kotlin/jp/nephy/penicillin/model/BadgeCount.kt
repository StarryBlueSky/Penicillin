package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement

@Suppress("UNUSED")
class BadgeCount(val json: JsonElement) {
    val dmUnreadCount by json.byInt("dm_unread_count")
    val ntabUnreadCount by json.byInt("ntab_unread_count")
    val totalUnreadCount by json.byInt("total_unread_count")
}
