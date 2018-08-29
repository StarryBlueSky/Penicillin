package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString


data class ExtendedProfile(override val json: JsonObject): PenicillinModel {
    val id by json.byLong
    val idStr by json.byString
    val birthdate by json.byModel<Birthdate?>()
}
