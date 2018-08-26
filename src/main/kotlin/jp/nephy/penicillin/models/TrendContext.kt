package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byStringList


class TrendContext(override val json: JsonObject): PenicillinModel {
    val relatedQuery by json.byStringList("query")
}
