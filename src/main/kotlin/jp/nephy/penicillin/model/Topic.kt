package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class Topic(val json: JsonElement) {
    val inline by json.byBool
    val roundedScore by json.byInt("rounded_score")
    val tokens by json.byList<SearchToken>()
    val topic by json.byString
}