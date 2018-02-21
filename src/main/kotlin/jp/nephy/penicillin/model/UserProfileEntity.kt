package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModelList

@Suppress("UNUSED")
class UserProfileEntity(override val json: JsonObject): JsonModel {
    val urls by json.byModelList<URLEntity>()
}
