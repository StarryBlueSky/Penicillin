package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString


data class Topic(override val json: JsonObject): PenicillinModel {
    val inline by json.byBool
    val roundedScore by json.byInt("rounded_score")
    val tokens by json.byModelList<SearchToken>()
    val topic by json.byString
}
