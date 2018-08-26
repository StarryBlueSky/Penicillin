package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byJsonArray
import jp.nephy.jsonkt.byString


class BoundingBox(override val json: JsonObject): PenicillinModel {
    val type by json.byString
    val coordinates by json.byJsonArray
    // [
    //   [
    //     [-77.119759, 38.791645],
    //     [-76.909393, 38.791645],
    //     [-76.909393, 38.995548],
    //     [-77.119759, 38.995548]
    //   ]
    // ]
}
