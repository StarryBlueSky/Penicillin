package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byUserProfileEntity

class UserEntity(val json: JsonElement) {
    val url by json.byUserProfileEntity
    val description by json.byUserProfileEntity
}
