package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel

data class UserGalleryData(override val json: JsonObject): PenicillinModel {
    val metadata by json.byModel<UserMetadata>()
    val data by json.byModel<User>()
}
