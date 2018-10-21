@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class URLEntity(override val json: ImmutableJsonObject): PenicillinModel {
    val url by string
    val expandedUrl by string("expanded_url")
    val displayUrl by string("display_url")
    val indices by intList
}
