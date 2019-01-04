@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byModelList
import jp.nephy.jsonkt.delegation.jsonObject
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class GeoResult(override val json: JsonObject): PenicillinModel {
    val query by model<Query>()
    private val jsonResult by jsonObject("result")
    val result by jsonResult.byModelList<Place>(key = "places")

    data class Query(override val json: JsonObject): PenicillinModel {
        val params by jsonObject
        val type by string
        val url by string
    }
}
