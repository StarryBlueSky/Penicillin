package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Privacy(override val json: JsonObject): PenicillinModel {
    val privacy by string
}
