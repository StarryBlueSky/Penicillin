package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Topic(override val json: JsonObject): PenicillinModel {
    val inline by boolean
    val roundedScore by int("rounded_score")
    val tokens by modelList<SearchToken>()
    val topic by string
}
