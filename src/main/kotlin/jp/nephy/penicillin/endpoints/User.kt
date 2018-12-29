@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.emulation.EmulationMode
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.User

class User(override val client: PenicillinClient): Endpoint {
    fun show(screenName: String, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/show.json") {
        parameter(
            "ext" to "mediaColor",
            "include_entities" to 1,
            "include_user_entities" to true,
            "include_profile_interstitial_type" to true,
            "include_user_symbol_entities" to true,
            "include_user_hashtag_entities" to true,
            "include_user_mention_entities" to true,
            emulationMode = EmulationMode.TwitterForiPhone
        )
        parameter("screen_name" to screenName, "include_entities" to includeEntities, *options)
    }.jsonObject<User>()

    fun show(userId: Long, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/show.json") {
        parameter(
                "ext" to "mediaColor",
                "include_entities" to 1,
                "include_user_entities" to true,
                "include_profile_interstitial_type" to true,
                "include_user_symbol_entities" to true,
                "include_user_hashtag_entities" to true,
                "include_user_mention_entities" to true,
                emulationMode = EmulationMode.TwitterForiPhone
        )
        parameter("user_id" to userId, "include_entities" to includeEntities, *options)
    }.jsonObject<User>()

    fun lookupByScreenNames(screenNames: List<String>, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/lookup.json") {
        parameter(
            "ext" to "mediaColor",
            "include_entities" to 1,
            "include_user_entities" to true,
            "include_profile_interstitial_type" to true,
            "include_profile_location" to true,
            "include_user_symbol_entities" to true,
            "include_user_hashtag_entities" to true,
            "include_user_mention_entities" to true,
            emulationMode = EmulationMode.TwitterForiPhone
        )
        parameter("screen_name" to screenNames.joinToString(","), "include_entities" to includeEntities, *options)
    }.jsonArray<User>()

    fun lookupByIds(userIds: List<Long>, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/lookup.json") {
        parameter(
                "ext" to "mediaColor",
                "include_entities" to 1,
                "include_user_entities" to true,
                "include_profile_interstitial_type" to true,
                "include_profile_location" to true,
                "include_user_symbol_entities" to true,
                "include_user_hashtag_entities" to true,
                "include_user_mention_entities" to true,
                emulationMode = EmulationMode.TwitterForiPhone
        )
        parameter("user_id" to userIds.joinToString(","), "include_entities" to includeEntities, *options)
    }.jsonArray<User>()

    fun profileBanner(screenName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/profile_banner.json") {
        parameter("screen_name" to screenName, *options)
    }.jsonObject<UserProfileBanner>()

    fun profileBanner(userId: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/profile_banner.json") {
        parameter("user_id" to userId, *options)
    }.jsonObject<UserProfileBanner>()

    fun search(q: String, page: Int? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/search.json") {
        parameter("q" to q, "page" to page, "count" to count, "include_entities" to includeEntities, *options)
    }.jsonArray<User>()

    fun userSuggestionCategories(lang: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/suggestions.json") {
        parameter("lang" to lang, *options)
    }.jsonArray<UserSuggestionCategory>()

    fun userSuggestion(slug: String, lang: String? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/suggestions/$slug.json") {
        parameter("lang" to lang, *options)
    }.jsonObject<UserSuggestion>()

    fun userSuggestions(slug: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/suggestions/$slug/members.json") {
        parameter(*options)
    }.jsonArray<User>()

    fun reportSpam(screenName: String, performBlock: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/users/report_spam.json") {
        body {
            form {
                add("screen_name" to screenName, "perform_block" to performBlock, *options)
            }
        }
    }.jsonObject<User>()

    fun reportSpam(userId: Long, performBlock: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/users/report_spam.json") {
        body {
            form {
                add("user_id" to userId, "perform_block" to performBlock, *options)
            }
        }
    }.jsonObject<User>()

    @PrivateEndpoint
    fun recommendations(screenName: String, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/recommendations.json") {
        parameter(
            "connections" to "true",
            "display_location" to "st-component",
            "ext" to "mediaColor",
            "include_entities" to "1",
            "include_profile_interstitial_type" to "true",
            "include_profile_location" to "true",
            "include_user_entities" to "true",
            "include_user_hashtag_entities" to "true",
            "include_user_mention_entities" to "true",
            "include_user_symbol_entities" to "true",
            "limit" to "3",
            "pc" to "true",
            "screen_name" to screenName,
            *options
        )
    }.jsonArray<Recommendation>()

    @PrivateEndpoint
    fun recommendations(userId: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/recommendations.json") {
        parameter(
                "connections" to "true",
                "display_location" to "st-component",
                "ext" to "mediaColor",
                "include_entities" to "1",
                "include_profile_interstitial_type" to "true",
                "include_profile_location" to "true",
                "include_user_entities" to "true",
                "include_user_hashtag_entities" to "true",
                "include_user_mention_entities" to "true",
                "include_user_symbol_entities" to "true",
                "limit" to "3",
                "pc" to "true",
                "user_id" to userId,
                *options
        )
    }.jsonArray<Recommendation>()

    @PrivateEndpoint
    fun extendedProfile(screenName: String, includeBirthdate: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.get("/1.1/users/extended_profile.json") {
        parameter("screen_name" to screenName, "include_birthdate" to includeBirthdate, *options)
    }.jsonObject<ExtendedProfile>()
}
