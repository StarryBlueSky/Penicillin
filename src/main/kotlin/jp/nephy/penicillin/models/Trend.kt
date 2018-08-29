package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byNullableJsonObject
import jp.nephy.jsonkt.byString


data class Trend(override val json: JsonObject): PenicillinModel {
    val name by json.byString
    val url by json.byString
    val promotedContent by json.byNullableJsonObject // null
    val query by json.byString
    val tweetVolume by json.byNullableInt
}
