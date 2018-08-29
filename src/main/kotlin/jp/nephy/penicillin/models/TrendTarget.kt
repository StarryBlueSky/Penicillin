package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byLongList
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byStringList


data class TrendTarget(override val json: JsonObject): PenicillinModel {
    val query by json.byString
    val pinnedTweets by json.byLongList("pinned_tweets")
    val pinnedTweetsStr by json.byStringList("pinned_tweets_string")
}
