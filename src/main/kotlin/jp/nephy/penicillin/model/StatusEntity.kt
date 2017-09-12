package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.*

class StatusEntity(val json: JsonElement) {
    val hashtags by json.byHashtagEntityArray
    val media by json.byNullableMediaEntityArray
    val symbols by json.bySymbolEntityArray
    val userMentions by json.byUserMentionEntityArray("user_mentions")
    val urls by json.byURLEntityArray
}
