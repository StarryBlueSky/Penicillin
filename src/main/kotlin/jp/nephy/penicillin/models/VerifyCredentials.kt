@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string

data class VerifyCredentials(val parentJson: ImmutableJsonObject): CommonUser(parentJson) {
    val email by string
    val phone by model<Phone>()
}
