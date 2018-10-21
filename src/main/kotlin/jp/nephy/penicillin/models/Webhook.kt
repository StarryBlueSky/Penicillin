@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class Webhook(override val json: ImmutableJsonObject): PenicillinModel {
    val id by string
    val url by string
    val valid by boolean
    val createdAt by string("created_at")
}
