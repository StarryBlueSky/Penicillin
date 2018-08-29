package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


data class TrendPlus(override val json: JsonObject): PenicillinModel {
    val id by json.byLong
    val metadata by json.byModel<TrendMetadata>()
    val trends by json.byModelList<TrendType>(key = "modules")
}
