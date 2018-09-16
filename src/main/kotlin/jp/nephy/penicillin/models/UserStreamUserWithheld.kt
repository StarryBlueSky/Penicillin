package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byStringList

data class UserStreamUserWithheld(override val json: JsonObject): PenicillinModel {
    val id by json["user_withheld"].byLong
    val withheldInCountries by json["user_withheld"].byStringList("withheld_in_countries")
}
