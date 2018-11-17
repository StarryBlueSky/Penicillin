@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byModelList
import jp.nephy.jsonkt.delegation.jsonObject
import jp.nephy.jsonkt.delegation.model

data class GeoResult(override val json: JsonObject): PenicillinModel {
    val query by model<GeoQuery>()
    private val jsonResult by jsonObject("result")
    val result by jsonResult.byModelList<Place>(key = "places")
}
