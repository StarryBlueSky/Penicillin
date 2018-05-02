package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byLongList
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byStringList


class TrendTarget(override val json: JsonObject): JsonModel {
    val query by json.byString
    val pinnedTweets by json.byLongList("pinned_tweets")
    val pinnedTweetsStr by json.byStringList("pinned_tweets_string")
}
