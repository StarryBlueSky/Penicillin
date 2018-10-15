package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class ProfileImageExtension(override val json: JsonObject): PenicillinModel {
    val mediaColor by model<MediaColor>()
}
