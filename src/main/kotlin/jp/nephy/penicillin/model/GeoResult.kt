package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byGeoQuery
import jp.nephy.penicillin.converter.byPlaceArray

class GeoResult(val json: JsonElement) {
    val query by json.byGeoQuery
    val result by json["result"].byPlaceArray("places")
}
