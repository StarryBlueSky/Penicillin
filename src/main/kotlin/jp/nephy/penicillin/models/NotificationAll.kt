package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.immutableJsonObjectList
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string
import jp.nephy.jsonkt.immutableJsonObject
import jp.nephy.jsonkt.nullableImmutableJsonObject
import jp.nephy.jsonkt.parse

data class NotificationAll(override val json: JsonObject): PenicillinModel {
    val globalObjects by model<GlobalObjects>()
    val timeline by model<Timeline>()

    data class GlobalObjects(override val json: JsonObject): PenicillinModel {
        val users by lambda { it.immutableJsonObject.values.map { json -> json.immutableJsonObject.parse<User>() } }
        val tweets by lambda { it.immutableJsonObject.values.map { json -> json.immutableJsonObject.parse<Status>() } }
        val notifications by lambda { it.immutableJsonObject.values.map { json -> json.immutableJsonObject.parse<Notification>() } }
    }

    data class Timeline(override val json: JsonObject): PenicillinModel {
        val id by string
        val instructions by immutableJsonObjectList
        val clearCache = instructions.find { it.contains("clearCache") }?.values?.firstOrNull()?.nullableImmutableJsonObject?.parse<NotificationInstruction.ClearCache>()
        val addEntries = instructions.find { it.contains("addEntries") }?.values?.firstOrNull()?.nullableImmutableJsonObject?.parse<NotificationInstruction.AddEntries>()
        val clearEntriesUnreadState = instructions.find { it.contains("clearEntriesUnreadState") }?.values?.firstOrNull()?.nullableImmutableJsonObject?.parse<NotificationInstruction.ClearEntriesUnreadState>()
        val markEntriesUnreadGreaterThanSortIndex = instructions.find { it.contains("markEntriesUnreadGreaterThanSortIndex") }?.values?.firstOrNull()?.nullableImmutableJsonObject?.parse<NotificationInstruction.MarkEntriesUnreadGreaterThanSortIndex>()
    }
}
