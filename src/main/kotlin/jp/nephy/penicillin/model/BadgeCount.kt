package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt


class BadgeCount(override val json: JsonObject): JsonModel {
    val dmUnreadCount by json.byInt("dm_unread_count")
    val ntabUnreadCount by json.byInt("ntab_unread_count")
    val totalUnreadCount by json.byInt("total_unread_count")
}
