package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class User(private val client: Client) {
    @GET
    fun getUser(screenName: String?=null, userId: Long?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/users/show.json")
                .paramIfOfficial("ext" to "mediaColor")
                .paramIfOfficial("include_entities" to 1)
                .paramIfOfficial("include_user_entities" to true)
                .paramIfOfficial("include_profile_interstitial_type" to true)
                .paramIfOfficial("include_user_symbol_entities" to true)
                .paramIfOfficial("include_user_hashtag_entities" to true)
                .paramIfOfficial("include_user_mention_entities" to true)
                .param("screen_name" to screenName)
                .param("user_id" to userId)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getUsers(screenNames: Array<String>?=null, userIds: Array<Long>?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<User> {
        return client.session.new()
                .url("/users/lookup.json")
                .paramIfOfficial("ext" to "mediaColor")
                .paramIfOfficial("include_entities" to 1)
                .paramIfOfficial("include_user_entities" to true)
                .paramIfOfficial("include_profile_interstitial_type" to true)
                .paramIfOfficial("include_profile_location" to true)
                .paramIfOfficial("include_user_symbol_entities" to true)
                .paramIfOfficial("include_user_hashtag_entities" to true)
                .paramIfOfficial("include_user_mention_entities" to true)
                .param("screen_name" to screenNames?.joinToString(","))
                .param("user_id" to userIds?.joinToString(","))
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getProfileBanner(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<UserProfileBanner> {
        return client.session.new()
                .url("/users/profile_banner.json")
                .param("screen_name" to screenName)
                .param("user_id" to userId)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun search(q: String, page: Int?=null, count: Int?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<User> {
        return client.session.new()
                .url("/users/search.json")
                .param("q" to q)
                .param("page" to page)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getSuggestionCategory(lang: String?=null, vararg options: Pair<String, String?>): ResponseList<UserSuggestionCategory> {
        return client.session.new()
                .url("/users/suggestions.json")
                .param("lang" to lang)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getSuggestions(slug: String, lang: String?=null, vararg options: Pair<String, String?>): ResponseObject<UserSuggestion> {
        return client.session.new()
                .url("/users/suggestions/$slug.json")
                .param("lang" to lang)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getSuggestedUsers(slug: String, vararg options: Pair<String, String?>): ResponseList<User> {
        return client.session.new()
                .url("/users/suggestions/$slug/members.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @POST
    fun reportSpam(screenName: String?=null, userId: Long?=null, performBlock: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/users/report_spam.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm("perform_block" to performBlock)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getRecommendations(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseList<Recommendation> {
        return client.session.new()
                .url("/users/recommendations.json")
                .param("connections" to "true")
                .param("display_location" to "st-component")
                .param("ext" to "mediaColor")
                .param("include_entities" to "1")
                .param("include_profile_interstitial_type" to "true")
                .param("include_profile_location" to "true")
                .param("include_user_entities" to "true")
                .param("include_user_hashtag_entities" to "true")
                .param("include_user_mention_entities" to "true")
                .param("include_user_symbol_entities" to "true")
                .param("limit" to "3")
                .param("pc" to "true")
                .param("screen_name" to screenName)
                .param("user_id" to userId)
                .params(*options)
                .get()
                .getResponseList()
    }
}
