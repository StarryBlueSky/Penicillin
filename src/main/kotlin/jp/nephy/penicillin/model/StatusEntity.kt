package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

@Suppress("UNUSED")
class StatusEntity(val json: JsonElement) {
    val hashtags by json.byList<HashtagEntity>()
    val media by json.byList<MediaEntity>()
    val symbols by json.byList<SymbolEntity>()
    val userMentions by json.byList<UserMentionEntity>("user_mentions")
    val urls by json.byList<URLEntity>()
}
