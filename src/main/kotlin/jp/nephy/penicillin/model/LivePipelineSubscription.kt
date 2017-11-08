package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byObject
import com.google.gson.JsonElement

@Suppress("UNUSED")
class LivePipelineSubscription(val json: JsonElement) {
    val subscriptions by json.byObject
}
