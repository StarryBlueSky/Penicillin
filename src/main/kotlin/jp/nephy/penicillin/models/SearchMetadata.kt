package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byFloat
import jp.nephy.jsonkt.byInt
import jp.nephy.jsonkt.byLong
import jp.nephy.jsonkt.byString


class SearchMetadata(override val json: JsonObject): PenicillinModel {
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
