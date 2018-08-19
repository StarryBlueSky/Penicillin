package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.UserStreamFilterLevel
import jp.nephy.penicillin.endpoint.parameter.UserStreamReplies
import jp.nephy.penicillin.endpoint.parameter.UserStreamWith
import jp.nephy.penicillin.request.streaming.FilterStreamListener
import jp.nephy.penicillin.request.streaming.SampleStreamListener
import jp.nephy.penicillin.request.streaming.UserStreamListener

class Stream(override val client: PenicillinClient): Endpoint {
    @Deprecated("UserStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Account Activity API (AAA)"))
    fun user(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, track: List<String>? = null, filterLevel: UserStreamFilterLevel? = null, language: String? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, count: Int? = null, includeFollowingsActivity: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getStream<UserStreamListener>("https://userstream.twitter.com/1.1/user.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "track" to track?.joinToString(","), "filter_level" to filterLevel?.value, "language" to language, "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), "count" to count, "include_followings_activity" to includeFollowingsActivity, *options)
    }

    @Deprecated("SiteStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Account Activity API (AAA)"))
    fun site(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, follow: List<Long>? = null, vararg options: Pair<String, Any?>) = client.session.getStream<UserStreamListener>("https://sitestream.twitter.com/1.1/site.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "follow" to follow?.joinToString(","), *options)
    }

    fun sample(delimited: String? = null, stallWarnings: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getStream<SampleStreamListener>("https://stream.twitter.com/1.1/statuses/sample.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, *options)
    }

    fun filter(delimited: String? = null, stallWarnings: Boolean? = null, track: List<String>? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, vararg options: Pair<String, Any?>) = client.session.postStream<FilterStreamListener>("https://stream.twitter.com/1.1/statuses/filter.json") {
        form("delimited" to delimited, "stall_warning" to stallWarnings, "track" to track?.joinToString(","), "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), *options)
    }
}
