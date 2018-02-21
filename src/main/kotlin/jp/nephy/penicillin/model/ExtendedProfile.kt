package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class ExtendedProfile(override val json: JsonObject): JsonModel {
    val id by json.byLong
    val idStr by json.byString
    val birthdate by json.byModel<Birthdate?>()
}
