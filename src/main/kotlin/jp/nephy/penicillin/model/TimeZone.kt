package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byString


class TimeZone(override val json: JsonObject): JsonModel {
    val name by json.byString
    val utcOffset by json.byInt("utc_offset")
    val tzinfoName by json.byString("tzinfo_name")
}
