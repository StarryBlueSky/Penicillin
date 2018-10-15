package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class VerifyCredentials(override val json: JsonObject): CommonUser() {
    val email by string
    val phone by model<Phone>()
}
