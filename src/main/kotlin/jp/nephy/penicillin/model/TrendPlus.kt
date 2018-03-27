package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class TrendPlus(override val json: JsonObject): JsonModel {
    val id by json.byLong
    val metadata by json.byModel<TrendMetadata>()
    val trends by json.byModelList<TrendType>(key = "modules")
}
