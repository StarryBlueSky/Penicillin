package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class UserProfileEntity(val json: JsonElement) {
    val urls by json.byList<URLEntity>()
}
