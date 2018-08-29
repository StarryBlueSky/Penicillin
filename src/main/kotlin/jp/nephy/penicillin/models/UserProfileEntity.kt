package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList


data class UserProfileEntity(override val json: JsonObject): PenicillinModel {
    val urls by json.byModelList<URLEntity>()
}
