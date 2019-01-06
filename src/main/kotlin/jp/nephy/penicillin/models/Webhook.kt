@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string

object Webhook {
    data class Model(override val json: JsonObject): PenicillinModel {
        val id by string
        val url by string
        val valid by boolean
        val createdAt by string("created_at")
    }

    data class List(override val json: JsonObject): PenicillinModel {
        val environments by modelList<Environment>()

        data class Environment(override val json: JsonObject): PenicillinModel {
            val name by string("environment_name")
            val webhooks by modelList<Model>()
        }
    }
}
