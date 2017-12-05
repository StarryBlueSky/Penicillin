package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byLambda
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class Coordinate(val json: JsonElement) {
    val coordinates by json.byList<Float>()
    val type by json.byString

    val longitude by json.byLambda {
        if (coordinates.size == 2) {
            coordinates[0]
        } else {
            null
        }
    }
    val latitude by json.byLambda {
        if (coordinates.size == 2) {
            coordinates[1]
        } else {
            null
        }
    }
}
