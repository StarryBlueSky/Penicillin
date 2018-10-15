package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class ExtendedProfile(override val json: JsonObject): PenicillinModel {
    val id by long
    val idStr by string
    val birthdate by model<Birthdate?>()
}
