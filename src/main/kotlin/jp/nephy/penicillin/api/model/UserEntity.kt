package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byUserProfileEntity

class UserEntity(val json: JsonElement) {
    val url by json.byUserProfileEntity
    val description by json.byUserProfileEntity
}
