@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class TrendType(override val json: JsonObject): PenicillinModel {
    val trend by model<Trend?>()
    val promotedTrend by model<PromotedTrend?>()

    data class Trend(override val json: JsonObject): PenicillinModel {
        val name by string
        val description by nullableString("meta_description")
        val rank by int
        val token by string
        val context by model<Context?>()
        val target by model<Target>()

        data class Context(override val json: JsonObject): PenicillinModel {
            val relatedQuery by stringList("query")
        }

        data class Target(override val json: JsonObject): PenicillinModel {
            val query by string
            val pinnedTweets by longList("pinned_tweets")
            val pinnedTweetsStr by stringList("pinned_tweets_string")
        }
    }

    data class PromotedTrend(override val json: JsonObject): PenicillinModel
}
