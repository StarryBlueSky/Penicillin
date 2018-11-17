@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

data class PinTweet(override val json: JsonObject): PenicillinModel {
    val pinnedTweets by longList("pinned_tweets")
}
