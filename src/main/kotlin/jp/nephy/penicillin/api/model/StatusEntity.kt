package jp.nephy.penicillin.api.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.byHashtagEntityArray
import jp.nephy.penicillin.api.bySymbolEntityArray
import jp.nephy.penicillin.api.byURLEntityArray
import jp.nephy.penicillin.api.byUserMentionEntityArray

class StatusEntity(val json: JsonElement) {
    val hashtags by json.byHashtagEntityArray
    val symbols by json.bySymbolEntityArray
    val userMentions by json.byUserMentionEntityArray("user_mentions")
    val urls by json.byURLEntityArray
}
