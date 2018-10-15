package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SearchToken(override val json: JsonObject): PenicillinModel {
    val token by string
}
