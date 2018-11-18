@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class ExtendedTweet(override val json: JsonObject): PenicillinModel {
    val displayTextRange by intList("display_text_range")
    val entities by model<StatusEntity>()
    val fullText by nullableString("full_text")
}
