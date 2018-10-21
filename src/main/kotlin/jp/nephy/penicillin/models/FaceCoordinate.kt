@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class FaceCoordinate(override val json: ImmutableJsonObject): PenicillinModel {
    val x by int
    val y by int
    val h by int
    val w by int
}
