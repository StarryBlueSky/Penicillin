@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class WebhookList(override val json: ImmutableJsonObject): PenicillinModel {
    val environments by modelList<Environment>()

    data class Environment(override val json: ImmutableJsonObject): PenicillinModel {
        val name by string("environment_name")
        val webhooks by modelList<Webhook>()
    }
}
