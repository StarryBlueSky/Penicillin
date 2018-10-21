@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Privacy(override val json: ImmutableJsonObject): PenicillinModel {
    val privacy by string
}
