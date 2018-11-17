@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class PushDevice(override val json: JsonObject): PenicillinModel {
    val appVersion by int("app_version")
    val availableLevels by int("available_levels")
    val clientApplicationId by int("client_application_id")
    val createdAt by string("created_at")
    val description by string
    val display by int
    val enabledFor by int("enabled_for")
    val environment by int
    val id by long
    val lang by string
    val token by string
    val udid by string
    val updatedAt by string("updated_at")
    val userId by long("user_id")
}
