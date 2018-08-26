package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList


class MomentTrendModule(override val json: JsonObject): PenicillinModel {
    val metadata by json.byModel<TrendMetadata>()
    val trends by json.byModelList<TrendType>()
}
