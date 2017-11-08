package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class TrendContext(val json: JsonElement) {
    val relatedQuery by json.byList<String>("query")
}
