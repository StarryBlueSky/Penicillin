@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class Search(override val json: JsonObject): PenicillinModel {
    val searchMetadata by model<SearchMetadata>(key = "search_metadata")
    val statuses by modelList<Status>()
}
