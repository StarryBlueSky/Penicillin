package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class Photo(override val json: JsonObject): PenicillinModel {
    val large by model<PhotoSize>()
    val medium by model<PhotoSize>()
    val small by model<PhotoSize>()
    val thumb by model<PhotoSize>()
}
