package jp.nephy.penicillin.api.result

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byLocationArray
import jp.nephy.penicillin.api.byTrendArray

class TrendsPlace(val json: JsonElement) {
    val asOf by json.byString("as_of") // "2017-09-09T08:47:26Z"
    val createdAt by json.byString("created_at") // "2017-09-09T08:43:56Z"
    val locations by json.byLocationArray
    val trends by json.byTrendArray
}
