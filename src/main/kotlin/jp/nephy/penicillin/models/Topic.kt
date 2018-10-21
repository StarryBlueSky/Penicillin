@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Topic(override val json: ImmutableJsonObject): PenicillinModel {
    val inline by boolean
    val roundedScore by int("rounded_score")
    val tokens by modelList<SearchToken>()
    val topic by string
}
