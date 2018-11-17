@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class MomentModule(override val json: JsonObject): PenicillinModel {
    val moduleType by string("module_type")
    val moments by modelList<Moment>()
}
