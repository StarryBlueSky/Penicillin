@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

data class PinTweet(override val json: ImmutableJsonObject): PenicillinModel {
    val pinnedTweets by longList("pinned_tweets")
}
