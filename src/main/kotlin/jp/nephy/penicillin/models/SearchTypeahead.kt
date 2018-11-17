@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class SearchTypeahead(override val json: JsonObject): PenicillinModel {
    val completedIn by float("completed_in")
    val hashtags by stringList
    val numResults by int("num_results")
    val oneclick by stringList
    val query by string
    val topics by modelList<Topic>()
    val users by modelList<UserTypeahead>()
}
