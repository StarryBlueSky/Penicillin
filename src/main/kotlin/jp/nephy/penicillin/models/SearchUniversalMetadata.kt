package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class SearchUniversalMetadata(override val json: JsonObject): PenicillinModel {
    val cursor by json.byString
    val refreshIntervalInSec by json.byInt("refresh_interval_in_sec")
}
