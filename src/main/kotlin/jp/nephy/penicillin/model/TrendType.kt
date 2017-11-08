package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class TrendType(val json: JsonElement) {
    val trend by json.byModel<TrendModule?>()
    val promotedTrend by json.byModel<PromotedTrendModule?>()
}
