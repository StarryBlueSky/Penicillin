package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.converter.byModel

class MomentTrendModule(val json: JsonElement) {
    val metadata by json.byModel<TrendMetadata>()
    val trends by json.byList<TrendType>()
}
