package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byURL

class Trend(val json: JsonElement) {
    val name by json.byString
    val url by json.byURL
    val promotedContent by json.byNullableObject // null
    val query by json.byString
    val tweetVolume by json.byNullableInt
}
