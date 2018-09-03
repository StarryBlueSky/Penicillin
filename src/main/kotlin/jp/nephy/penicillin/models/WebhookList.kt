package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString

data class WebhookList(override val json: JsonObject): PenicillinModel {
    val environments by json.byModelList<Environment>()

    data class Environment(override val json: JsonObject): PenicillinModel {
        val name by json.byString("environment_name")
        val webhooks by json.byModelList<Webhook>()
    }
}
