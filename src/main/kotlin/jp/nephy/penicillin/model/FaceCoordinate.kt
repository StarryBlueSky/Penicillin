package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement

@Suppress("UNUSED")
class FaceCoordinate(val json: JsonElement) {
    val x by json.byInt
    val y by json.byInt
    val h by json.byInt
    val w by json.byInt
}
