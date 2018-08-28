package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList


class Face(override val json: JsonObject): PenicillinModel {
    val faces by json.byModelList<FaceCoordinate>()
}
