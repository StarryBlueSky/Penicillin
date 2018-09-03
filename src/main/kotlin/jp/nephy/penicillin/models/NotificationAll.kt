package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

data class NotificationAll(override val json: JsonObject): PenicillinModel {
    val globalObjects by json.byModel<GlobalObjects>()
    val timeline by json.byModel<Timeline>()

    data class GlobalObjects(override val json: JsonObject): PenicillinModel {
        val users by json.byLambda { jsonObject.values.map { it.jsonObject.parse<User>() } }
        val tweets by json.byLambda { jsonObject.values.map { it.jsonObject.parse<Status>() } }
        val notifications by json.byLambda { jsonObject.values.map { it.jsonObject.parse<Notification>() } }
    }

    data class Timeline(override val json: JsonObject): PenicillinModel {
        val id by json.byString
        val instructions by json.byJsonObjectList
        val clearCache = instructions.find { it.contains("clearCache") }?.values?.firstOrNull()?.jsonObject?.parse<NotificationInstruction.ClearCache>()
        val addEntries = instructions.find { it.contains("addEntries") }?.values?.firstOrNull()?.jsonObject?.parse<NotificationInstruction.AddEntries>()
        val clearEntriesUnreadState = instructions.find { it.contains("clearEntriesUnreadState") }?.values?.firstOrNull()?.jsonObject?.parse<NotificationInstruction.ClearEntriesUnreadState>()
        val markEntriesUnreadGreaterThanSortIndex = instructions.find { it.contains("markEntriesUnreadGreaterThanSortIndex") }?.values?.firstOrNull()?.jsonObject?.parse<NotificationInstruction.MarkEntriesUnreadGreaterThanSortIndex>()
    }
}
