package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byJsonObject


class LivePipelineSubscription(override val json: JsonObject): JsonModel {
    val subscriptions by json.byJsonObject
}
