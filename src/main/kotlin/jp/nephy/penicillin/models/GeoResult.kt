package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class GeoResult(override val json: JsonObject): PenicillinModel {
    val query by json.byModel<GeoQuery>()
    val result by json["result"].byModelList<Place>(key = "places")
}
