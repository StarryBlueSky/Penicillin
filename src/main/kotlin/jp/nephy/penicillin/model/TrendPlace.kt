package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class TrendPlace(val json: JsonElement) {
    val asOf by json.byString("as_of")
    val createdAt by json.byString("created_at")
    val locations by json.byList<Location>()
    val trends by json.byList<Trend>()
}
