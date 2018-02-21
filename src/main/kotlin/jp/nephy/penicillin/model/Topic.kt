package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

@Suppress("UNUSED")
class Topic(override val json: JsonObject): JsonModel {
    val inline by json.byBool
    val roundedScore by json.byInt("rounded_score")
    val tokens by json.byModelList<SearchToken>()
    val topic by json.byString
}
