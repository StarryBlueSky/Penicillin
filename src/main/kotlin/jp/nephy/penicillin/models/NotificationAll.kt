@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.jsonObjectList
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string
import jp.nephy.jsonkt.jsonObjectOrNull
import jp.nephy.jsonkt.parse

data class NotificationAll(override val json: JsonObject): PenicillinModel {
    val globalObjects by model<GlobalObjects>()
    val timeline by model<Timeline>()

    data class GlobalObjects(override val json: JsonObject): PenicillinModel {
        val users by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<User>() } }
        val tweets by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<Status>() } }
        val notifications by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<Notification>() } }
    }

    data class Timeline(override val json: JsonObject): PenicillinModel {
        val id by string
        private val instructions by jsonObjectList
        val clearCache = instructions.find { it.contains("clearCache") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.ClearCache>()
        val addEntries = instructions.find { it.contains("addEntries") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.AddEntries>()
        val clearEntriesUnreadState = instructions.find { it.contains("clearEntriesUnreadState") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.ClearEntriesUnreadState>()
        val markEntriesUnreadGreaterThanSortIndex = instructions.find { it.contains("markEntriesUnreadGreaterThanSortIndex") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.MarkEntriesUnreadGreaterThanSortIndex>()
    }
}
