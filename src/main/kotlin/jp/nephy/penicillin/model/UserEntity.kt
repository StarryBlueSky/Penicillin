package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class UserEntity(val json: JsonElement) {
    val url by json.byModel<UserProfileEntity>()
    val description by json.byModel<UserProfileEntity>()
}
