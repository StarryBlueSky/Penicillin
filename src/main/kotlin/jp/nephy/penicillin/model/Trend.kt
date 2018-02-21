package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

@Suppress("UNUSED")
class Trend(override val json: JsonObject): JsonModel {
    val name by json.byString
    val url by json.byUrl
    val promotedContent by json.byNullableJsonObject // null
    val query by json.byString
    val tweetVolume by json.byNullableInt
}
