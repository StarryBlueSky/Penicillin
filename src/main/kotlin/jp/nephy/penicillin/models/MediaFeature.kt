package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class MediaFeature(override val json: JsonObject): PenicillinModel {
    val large by model<Face>()
    val medium by model<Face>()
    val orig by model<Face>()
    val small by model<Face>()
}
