package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class SearchUniversalMetadata(override val json: JsonObject): JsonModel {
    val cursor by json.byString
    val refreshIntervalInSec by json.byInt("refresh_interval_in_sec")
}