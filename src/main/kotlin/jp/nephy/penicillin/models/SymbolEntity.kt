package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class SymbolEntity(override val json: JsonObject): PenicillinModel {
    val text by string
    val indices by intList
}

