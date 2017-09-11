package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import com.github.salomonbrys.kotson.*
import jp.nephy.penicillin.api.byURL

class Embed(val json: JsonElement) {
    val authorName by json.byString("author_name")
    val authorUrl by json.byURL("author_url")
    val cacheAge by json.byString("cache_age")
    val height by json.byNullableInt
    val html by json.byString
    val providerName by json.byString("provider_name")
    val providerUrl by json.byURL("provider_url")
    val type by json.byString
    val url by json.byURL
    val version by json.byString
    val width by json.byNullableInt
}
