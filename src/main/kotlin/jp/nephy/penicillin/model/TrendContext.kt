package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byStringList


class TrendContext(override val json: JsonObject): JsonModel {
    val relatedQuery by json.byStringList("query")
}
