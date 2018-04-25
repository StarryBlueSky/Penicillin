package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLongList

class PinTweet(override val json: JsonObject): JsonModel {
    val pinnedTweets by json.byLongList("pinned_tweets")
}
