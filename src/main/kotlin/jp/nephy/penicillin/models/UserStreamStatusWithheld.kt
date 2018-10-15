package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byLong
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.byStringList
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class UserStreamStatusWithheld(override val json: JsonObject): PenicillinModel {
    private val statusWithheld by immutableJsonObject("status_withheld")

    val userId by statusWithheld.byLong("user_id")
    val id by statusWithheld.byLong
    val timestampMs by statusWithheld.byString("timestamp_ms")
    val withheldInCountries by statusWithheld.byStringList("withheld_in_countries")
}
