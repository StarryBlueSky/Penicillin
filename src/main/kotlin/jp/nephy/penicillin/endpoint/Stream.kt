package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.UserStreamFilterLevel
import jp.nephy.penicillin.endpoint.parameter.UserStreamReplies
import jp.nephy.penicillin.endpoint.parameter.UserStreamWith
import jp.nephy.penicillin.streaming.FilterStreamHandler
import jp.nephy.penicillin.streaming.SampleStreamHandler
import jp.nephy.penicillin.streaming.UserStreamHandler


class Stream(override val client: PenicillinClient): Endpoint {
    fun userStream(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, track: List<String>? = null, filterLevel: UserStreamFilterLevel? = null, language: String? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, count: Int? = null, includeFollowingsActivity: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getStream<UserStreamHandler>("https://userstream.twitter.com/1.1/user.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "track" to track?.joinToString(","), "filter_level" to filterLevel?.value, "language" to language, "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), "count" to count, "include_followings_activity" to includeFollowingsActivity, *options)
    }

    fun siteStream(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, follow: List<Long>? = null, vararg options: Pair<String, Any?>) = client.session.getStream<UserStreamHandler>("https://sitestream.twitter.com/1.1/site.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "follow" to follow?.joinToString(","), *options)
    }

    fun sampleStream(delimited: String? = null, stallWarnings: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getStream<SampleStreamHandler>("https://stream.twitter.com/1.1/statuses/sample.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, *options)
    }

    fun filterStream(delimited: String? = null, stallWarnings: Boolean? = null, track: List<String>? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, vararg options: Pair<String, Any?>) = client.session.postStream<FilterStreamHandler>("https://stream.twitter.com/1.1/statuses/filter.json") {
        query("delimited" to delimited, "stall_warning" to stallWarnings, "track" to track?.joinToString(","), "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), *options)
    }
}
