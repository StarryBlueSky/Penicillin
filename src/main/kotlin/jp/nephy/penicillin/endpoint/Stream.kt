package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.parameters.UserStreamFilterLevel
import jp.nephy.penicillin.parameters.UserStreamReplies
import jp.nephy.penicillin.parameters.UserStreamWith
import jp.nephy.penicillin.response.ResponseStream

class Stream(private val client: Client) {
    @GET
    fun getUserStream(delimited: String?=null, stallWarnings: Boolean?=null, with: UserStreamWith?=null, replies: UserStreamReplies?=null, track: Array<String>?=null, filterLevel: UserStreamFilterLevel?=null, language: String?=null, follow: Array<Long>?=null, locations: Pair<Float, Float>?=null, count: Int?=null, includeFollowingsActivity: Boolean?=null, vararg options: Pair<String, String?>): ResponseStream {
        return client.session.new()
                .url("https://userstream.twitter.com/1.1/user.json")
                .param("delimited" to delimited)
                .param("stall_warning" to stallWarnings)
                .param("with" to when (with) {
                    UserStreamWith.User -> "user"
                    UserStreamWith.Followings -> "followings"
                    else -> null
                })
                .param("replies" to when (replies) {
                    UserStreamReplies.All -> "all"
                    else -> null
                })
                .param("track" to track?.joinToString(","))
                .param("filter_level" to when (filterLevel) {
                    UserStreamFilterLevel.Low -> "low"
                    UserStreamFilterLevel.Midium -> "midium"
                    UserStreamFilterLevel.None -> "none"
                    else -> null
                })
                .param("language" to language)
                .param("follow" to follow?.joinToString(","))
                .param("locations" to locations?.toList()?.joinToString(","))
                .param("count" to count)
                .param("include_followings_activity" to includeFollowingsActivity)
                .params(*options)
                .get()
                .getResponseStream()
    }
}
