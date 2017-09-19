package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class TrendModule(val json: JsonElement) {
    val name by json["trend"].byString
    val description by json["trend"].byString("meta_description")
    val rank by json["trend"].byInt
    val token by json["trend"].byString
    val relatedQueries by json["trend"]["context"].byList<String>("related_queries")
    val targetQuery by json["trend"]["target"].byString("query")
}