package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModelList

@Suppress("UNUSED")
class Face(override val json: JsonObject): JsonModel {
    val faces by json.byModelList<FaceCoordinate>()
}
