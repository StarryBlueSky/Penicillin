@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Embed(override val json: JsonObject): PenicillinModel {
    val authorName by string("author_name")
    val authorUrl by string("author_url")
    val cacheAge by string("cache_age")
    val height by nullableInt
    val html by string
    val providerName by string("provider_name")
    val providerUrl by string("provider_url")
    val type by string
    val url by string
    val version by string
    val width by nullableInt
}
