package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel

class UserEntity(override val json: JsonObject): PenicillinModel {
    val url by json.byModel<UserProfileEntity>()
    val description by json.byModel<UserProfileEntity>()
}
