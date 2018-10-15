package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Image(override val json: JsonObject): PenicillinModel {
    val imageType by string("image_type")
    val w by int
    val h by int
}

