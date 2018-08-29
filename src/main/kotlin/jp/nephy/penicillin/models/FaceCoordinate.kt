package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt


data class FaceCoordinate(override val json: JsonObject): PenicillinModel {
    val x by json.byInt
    val y by json.byInt
    val h by json.byInt
    val w by json.byInt
}
