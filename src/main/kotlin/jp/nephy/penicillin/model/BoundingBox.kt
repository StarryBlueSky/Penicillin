package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byArray
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class BoundingBox(val json: JsonElement) {
    val type by json.byString
    val coordinates by json.byArray
    // [
    //   [
    //     [-77.119759, 38.791645],
    //     [-76.909393, 38.791645],
    //     [-76.909393, 38.995548],
    //     [-77.119759, 38.995548]
    //   ]
    // ]
}
