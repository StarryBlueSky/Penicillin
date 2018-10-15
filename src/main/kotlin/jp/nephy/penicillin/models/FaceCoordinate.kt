package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class FaceCoordinate(override val json: JsonObject): PenicillinModel {
    val x by int
    val y by int
    val h by int
    val w by int
}
