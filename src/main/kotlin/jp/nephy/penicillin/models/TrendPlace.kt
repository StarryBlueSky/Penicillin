package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


data class TrendPlace(override val json: JsonObject): PenicillinModel {
    val asOf by json.byString("as_of")
    val createdAt by json.byString("created_at")
    val locations by json.byModelList<Location>()
    val trends by json.byModelList<Trend>()
}
