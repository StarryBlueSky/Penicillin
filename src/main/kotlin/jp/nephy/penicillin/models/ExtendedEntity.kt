@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class ExtendedEntity(override val json: ImmutableJsonObject): PenicillinModel {
    val displayTextRange by intList("display_text_range")
    val media by modelList<MediaEntity>()
    val fullText by nullableString("full_text")
}
