package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byFloatList
import jp.nephy.jsonkt.byLambda
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class Coordinate(override val json: JsonObject): JsonModel {
    val coordinates by json.byFloatList
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
