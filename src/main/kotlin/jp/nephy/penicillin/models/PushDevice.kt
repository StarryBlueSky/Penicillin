package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString


data class PushDevice(override val json: JsonObject): PenicillinModel {
    val appVersion by json.byInt("app_version")
    val availableLevels by json.byInt("available_levels")
    val clientApplicationId by json.byInt("client_application_id")
    val createdAt by json.byString("created_at")
    val description by json.byString
    val display by json.byInt
    val enabledFor by json.byInt("enabled_for")
    val environment by json.byInt
    val id by json.byLong
    val lang by json.byString
    val token by json.byString
    val udid by json.byString
    val updatedAt by json.byString("updated_at")
    val userId by json.byLong("user_id")
}
