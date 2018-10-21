@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class StatusEntity(override val json: ImmutableJsonObject): PenicillinModel {
    val hashtags by modelList<HashtagEntity>()
    val media by modelList<MediaEntity>()
    val symbols by modelList<SymbolEntity>()
    val userMentions by modelList<UserMentionEntity>(key = "user_mentions")
    val urls by modelList<URLEntity>()
}
