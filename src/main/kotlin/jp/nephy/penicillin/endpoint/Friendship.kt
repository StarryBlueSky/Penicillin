package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class Friendship(private val client: Client) {
    @GET
    fun getRelationship(sourceId: Long?=null, sourceScreenName: String?=null, targetId: Long?=null, targetScreenName: String?=null, vararg options: Pair<String, String?>): ResponseObject<FriendshipsShow> {
        return client.session.new()
                .url("/friendships/show.json")
                .param("source_id" to sourceId)
                .param("source_screen_name" to sourceScreenName)
                .param("target_id" to targetId)
                .param("target_screen_name" to targetScreenName)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getRelationships(screenNames: Array<String>?=null, userIds: Array<Long>?=null, vararg options: Pair<String, String?>): ResponseList<FriendshipsLookup> {
        return client.session.new()
                .url("/friendships/lookup.json")
                .param("screen_name" to screenNames?.joinToString(","))
                .param("user_id" to userIds?.joinToString(","))
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getNoRetweetsIds(stringifyIds: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<FriendshipsNoRetweetsIds> {
        return client.session.new()
                .url("/friendships/no_retweets/ids.json")
                .param("stringify_ids" to stringifyIds)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getReceivedFollowRequests(stringifyIds: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<CursorIds> {
        return client.session.new()
                .url("/friendships/incoming.json")
                .param("stringify_ids" to stringifyIds)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getSentFollowRequests(stringifyIds: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<CursorIds> {
        return client.session.new()
                .url("/friendships/outgoing.json")
                .param("stringify_ids" to stringifyIds)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun create(screenName: String?=null, userId: Long?=null, follow: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/friendships/create.json")
                .dataAsFormIfOfficial("ext" to "mediaColor")
                .dataAsFormIfOfficial("handles_challenges" to "1")
                .dataAsFormIfOfficial("include_entities" to "1")
                .dataAsFormIfOfficial("include_profile_interstitial_type" to "true")
                .dataAsFormIfOfficial("include_profile_location" to "true")
                .dataAsFormIfOfficial("include_user_entities" to "true")
                .dataAsFormIfOfficial("include_user_hashtag_entities" to "true")
                .dataAsFormIfOfficial("include_user_mention_entities" to "true")
                .dataAsFormIfOfficial("include_user_symbol_entities" to "true")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm("follow" to follow)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/friendships/destroy.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun update(screenName: String?=null, userId: Long?=null, devide: Boolean?=null, retweets: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Relationship> {
        return client.session.new()
                .url("/friendships/update.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm("device" to devide)
                .dataAsForm("retweets" to retweets)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}