package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject
import jp.nephy.penicillin.result.UserProfileBannerModel
import jp.nephy.penicillin.result.UserSuggestionModel
import jp.nephy.penicillin.result.UserSuggestionSlugModel

class User(private val client: Client) {
    @GET
    fun getUser(screenName: String?=null, userId: Long?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/users/show.json")
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
                .param("screen_name" to screenNames?.joinToString(","))
                .param("user_id" to userIds?.joinToString(","))
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getProfileBanner(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<UserProfileBannerModel> {
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
    fun getSuggestionCategory(lang: String?=null, vararg options: Pair<String, String?>): ResponseList<UserSuggestionModel> {
        return client.session.new()
                .url("/users/suggestions.json")
                .param("lang" to lang)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getSuggestions(slug: String, lang: String?=null, vararg options: Pair<String, String?>): ResponseObject<UserSuggestionSlugModel> {
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
    fun reportSpam(screenName: String?=null, userId: Long?=null, performBlock: Boolean?=null): ResponseObject<User> {
        return client.session.new()
                .url("/users/report_spam.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm("perform_block" to performBlock)
                .post()
                .getResponseObject()
    }
}
