package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class TimeZone(val json: JsonElement) {
    val name by json.byString
    val utcOffset by json.byInt("utc_offset")
    val tzinfoName by json.byString("tzinfo_name")
}
