package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byNullableInt
import jp.nephy.jsonkt.byNullableLong
import jp.nephy.jsonkt.byString

data class UserStreamWarning(override val json: JsonObject): PenicillinModel {
    val code by json["warning"].byInt
    val message by json["warning"].byString
    val percentFull by json["warning"].byNullableInt("percent_full")
    val userId by json["warning"].byNullableLong
}
