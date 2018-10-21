@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Face(override val json: ImmutableJsonObject): PenicillinModel {
    val faces by modelList<FaceCoordinate>()
}
