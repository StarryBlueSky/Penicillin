package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel

class UserGalleryData(override val json: JsonObject): JsonModel {
    val metadata by json.byModel<UserMetadata>()
    val data by json.byModel<User>()
}
