package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class GeoResult(override val json: JsonObject): JsonModel {
    val query by json.byModel<GeoQuery>()
    val result by json["result"].byModelList<Place>(key = "places")
}
