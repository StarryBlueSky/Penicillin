@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SearchMetadata(override val json: JsonObject): PenicillinModel {
    val completedIn by float("completed_in")
    val count by int
    val maxId by long("max_id")
    val maxIdStr by string("max_id_str")
    val nextResults by string("next_results")
    val query by string
    val refreshUrl by string("refresh_url")
    val sinceId by int("since_id")
    val sinceIdStr by string("since_id_str")
}
