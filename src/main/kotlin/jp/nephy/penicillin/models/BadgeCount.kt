package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt


data class BadgeCount(override val json: JsonObject): PenicillinModel {
    val dmUnreadCount by json.byInt("dm_unread_count")
    val ntabUnreadCount by json.byInt("ntab_unread_count")
    val totalUnreadCount by json.byInt("total_unread_count")
}
