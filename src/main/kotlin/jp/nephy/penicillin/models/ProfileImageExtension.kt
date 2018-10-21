@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class ProfileImageExtension(override val json: ImmutableJsonObject): PenicillinModel {
    val mediaColor by model<MediaColor>()
}
