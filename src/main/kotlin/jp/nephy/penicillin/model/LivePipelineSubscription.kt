package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byObject
import com.google.gson.JsonElement

class LivePipelineSubscription(val json: JsonElement) {
    val subscriptions by json.byObject
}
