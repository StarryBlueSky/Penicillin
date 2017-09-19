package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

class GeoResult(val json: JsonElement) {
    val query by json.byModel<GeoQuery>()
    val result by json["result"].byList<Place>("places")
}
