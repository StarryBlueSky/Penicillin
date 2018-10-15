package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class SearchUniversalMetadata(override val json: JsonObject): PenicillinModel {
    val cursor by string
    val refreshIntervalInSec by int("refresh_interval_in_sec")
}
