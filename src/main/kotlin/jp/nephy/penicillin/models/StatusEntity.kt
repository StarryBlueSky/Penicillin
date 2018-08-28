package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModelList


class StatusEntity(override val json: JsonObject): PenicillinModel {
    val hashtags by json.byModelList<HashtagEntity>()
    val media by json.byModelList<MediaEntity>()
    val symbols by json.byModelList<SymbolEntity>()
    val userMentions by json.byModelList<UserMentionEntity>(key = "user_mentions")
    val urls by json.byModelList<URLEntity>()
}
