package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


class TrendPlace(override val json: JsonObject): JsonModel {
    val asOf by json.byString("as_of")
    val createdAt by json.byString("created_at")
    val locations by json.byModelList<Location>()
    val trends by json.byModelList<Trend>()
}
