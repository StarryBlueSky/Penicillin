package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byURLEntityArray

class UserProfileEntity(val json: JsonElement) {
    val urls by json.byURLEntityArray
}
