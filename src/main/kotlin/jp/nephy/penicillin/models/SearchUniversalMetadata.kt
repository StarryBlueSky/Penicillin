@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class SearchUniversalMetadata(override val json: ImmutableJsonObject): PenicillinModel {
    val cursor by string
    val refreshIntervalInSec by int("refresh_interval_in_sec")
}
