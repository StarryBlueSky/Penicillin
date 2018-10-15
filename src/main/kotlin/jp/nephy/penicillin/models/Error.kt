package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Error(override val json: JsonObject): PenicillinModel {
    val code by int
    val name by nullableString
    val message by string
}
