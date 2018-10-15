package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.nullableImmutableJsonObject
import jp.nephy.jsonkt.delegation.nullableInt
import jp.nephy.jsonkt.delegation.string

data class Trend(override val json: JsonObject): PenicillinModel {
    val name by string
    val url by string
    val promotedContent by nullableImmutableJsonObject // null
    val query by string
    val tweetVolume by nullableInt
}
