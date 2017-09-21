package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.misc.StatusID

class TrendTarget(val json: JsonElement) {
    val query by json.byString
    val pinnedTweets by json.byList<StatusID>("pinned_tweets", {it.asLong})
    val pinnedTweetsStr by json.byList<String>("pinned_tweets_string")
}
