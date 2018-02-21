package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel

class UserEntity(override val json: JsonObject): JsonModel {
    val url by json.byModel<UserProfileEntity>()
    val description by json.byModel<UserProfileEntity>()
}
