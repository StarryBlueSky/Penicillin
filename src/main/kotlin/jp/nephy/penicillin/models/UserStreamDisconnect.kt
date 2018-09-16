package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString

data class UserStreamDisconnect(override val json: JsonObject): PenicillinModel {
    val code by json["disconnect"].byInt
    val streamName by json["disconnect"].byNullableString("stream_name")
    val reason by json["disconnect"].byString
}
