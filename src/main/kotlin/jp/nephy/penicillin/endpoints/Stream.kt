@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.streaming.*
import jp.nephy.penicillin.endpoints.parameters.UserStreamFilterLevel
import jp.nephy.penicillin.endpoints.parameters.UserStreamReplies
import jp.nephy.penicillin.endpoints.parameters.UserStreamWith

class Stream(override val client: PenicillinClient): Endpoint {
    @Deprecated("UserStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
    fun user(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, track: List<String>? = null, filterLevel: UserStreamFilterLevel? = null, language: String? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, count: Int? = null, includeFollowingsActivity: Boolean? = null, stringifyFriendIds: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/user.json", EndpointHost.UserStream) {
        parameter("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "track" to track?.joinToString(","), "filter_level" to filterLevel?.value, "language" to language, "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), "count" to count, "include_followings_activity" to includeFollowingsActivity, "stringify_friend_ids" to stringifyFriendIds, *options)
    }.stream<UserStreamListener, UserStreamHandler>()

    @Deprecated("SiteStream API retired on August 23th, 2018.", replaceWith = ReplaceWith("Tweetstorm or Account Activity API (AAA)"))
    fun site(delimited: String? = null, stallWarnings: Boolean? = null, with: UserStreamWith? = null, replies: UserStreamReplies? = null, follow: List<Long>? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/site.json", EndpointHost.SiteStream) {
        parameter("delimited" to delimited, "stall_warning" to stallWarnings, "with" to with?.value, "replies" to replies?.value, "follow" to follow?.joinToString(","), *options)
    }.stream<UserStreamListener, UserStreamHandler>()

    fun sample(delimited: String? = null, stallWarnings: Boolean? = null, language: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/statuses/sample.json", EndpointHost.Stream) {
        parameter("delimited" to delimited, "stall_warning" to stallWarnings, "language" to language, *options)
    }.stream<SampleStreamListener, SampleStreamHandler>()

    fun filter(delimited: String? = null, stallWarnings: Boolean? = null, track: List<String>? = null, follow: List<Long>? = null, locations: Pair<Float, Float>? = null, language: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/statuses/filter.json", EndpointHost.Stream) {
        parameter("delimited" to delimited, "stall_warning" to stallWarnings, "track" to track?.joinToString(","), "follow" to follow?.joinToString(","), "locations" to locations?.toList()?.joinToString(","), "language" to language, *options)
    }.stream<FilterStreamListener, FilterStreamHandler>()
}
