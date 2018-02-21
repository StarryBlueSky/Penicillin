package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt

@Suppress("UNUSED")
class FaceCoordinate(override val json: JsonObject): JsonModel {
    val x by json.byInt
    val y by json.byInt
    val h by json.byInt
    val w by json.byInt
}
