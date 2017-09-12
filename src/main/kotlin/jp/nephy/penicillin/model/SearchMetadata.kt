package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byFloat
import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byLong
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement

class SearchMetadata(val json: JsonElement) {
    val completedIn by json.byFloat("completed_in")
    val count by json.byInt
    val maxId by json.byLong("max_id")
    val maxIdStr by json.byString("max_id_str")
    val nextResults by json.byString("next_results")
    val query by json.byString
    val refreshUrl by json.byString("refresh_url")
    val sinceId by json.byInt("since_id")
    val sinceIdStr by json.byString("since_id_str")
}
