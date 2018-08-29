package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList

data class UserGalleryModule(override val json: JsonObject): PenicillinModel {
    val metadata by json.byModel<UserGalleryMetadata>()
    val data by json.byModelList<UserGalleryData>()
}
