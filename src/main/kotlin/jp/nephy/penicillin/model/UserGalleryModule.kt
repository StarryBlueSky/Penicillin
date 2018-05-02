package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList

class UserGalleryModule(override val json: JsonObject): JsonModel {
    val metadata by json.byModel<UserGalleryMetadata>()
    val data by json.byModelList<UserGalleryData>()
}
