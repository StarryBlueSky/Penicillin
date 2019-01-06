@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.intList
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string

data class StatusEntity(override val json: JsonObject): PenicillinModel {
    val hashtags by modelList<HashtagEntity>()
    val media by modelList<MediaEntity>()
    val symbols by modelList<SymbolEntity>()
    val userMentions by modelList<UserMentionEntity>(key = "user_mentions")
    val urls by modelList<URLEntity>()

    data class HashtagEntity(override val json: JsonObject): PenicillinModel {
        val text by string
        val indices by intList
    }

    data class UserMentionEntity(override val json: JsonObject): PenicillinModel {
        val screenName by string("screen_name")
        val name by string
        val id by long
        val idStr by string("id_str")
        val indices by intList()
    }

    data class SymbolEntity(override val json: JsonObject): PenicillinModel {
        val text by string
        val indices by intList
    }
}
