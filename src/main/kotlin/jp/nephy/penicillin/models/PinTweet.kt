package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList

class PinTweet(override val json: JsonObject): PenicillinModel {
    val pinnedTweets by json.byLongList("pinned_tweets")
}
