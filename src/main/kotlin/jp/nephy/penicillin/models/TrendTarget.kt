package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*


data class TrendTarget(override val json: JsonObject): PenicillinModel {
    val query by string
    val pinnedTweets by longList("pinned_tweets")
    val pinnedTweetsStr by stringList("pinned_tweets_string")
}
