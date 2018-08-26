package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


class TrendType(override val json: JsonObject): PenicillinModel {
    val trend by json.byModel<TrendModule?>()
    val promotedTrend by json.byModel<PromotedTrendModule?>()
}
