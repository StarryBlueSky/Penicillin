package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.misc.StatusID

@Suppress("UNUSED")
class TrendTarget(override val json: JsonObject): JsonModel {
    val query by json.byString
    val pinnedTweets by json.byLambdaList("pinned_tweets") { StatusID(long) }
    val pinnedTweetsStr by json.byStringList("pinned_tweets_string")
}
