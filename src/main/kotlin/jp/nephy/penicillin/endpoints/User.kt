package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.*
import jp.nephy.penicillin.models.User


class User(override val client: PenicillinClient): Endpoint {
    fun show(screenName: String? = null, userId: Long? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<User>("/users/show.json") {
        query("ext" to "mediaColor", "include_entities" to 1, "include_user_entities" to true, "include_profile_interstitial_type" to true, "include_user_symbol_entities" to true, "include_user_hashtag_entities" to true, "include_user_mention_entities" to true, onlyOfficialClient = true)
        query("screen_name" to screenName, "user_id" to userId, "include_entities" to includeEntities, *options)
    }

    fun lookup(screenNames: List<String>? = null, userIds: List<Long>? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<User>("/users/lookup.json") {
        query("ext" to "mediaColor", "include_entities" to 1, "include_user_entities" to true, "include_profile_interstitial_type" to true, "include_profile_location" to true, "include_user_symbol_entities" to true, "include_user_hashtag_entities" to true, "include_user_mention_entities" to true, onlyOfficialClient = true)
        query("screen_name" to screenNames?.joinToString(","), "user_id" to userIds?.joinToString(","), "include_entities" to includeEntities, *options)
    }

    fun profileBanner(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.getObject<UserProfileBanner>("/users/profile_banner.json") {
        query("screen_name" to screenName, "user_id" to userId, *options)
    }

    fun search(q: String, page: Int? = null, count: Int? = null, includeEntities: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getList<User>("/users/search.json") {
        query("q" to q, "page" to page, "count" to count, "include_entities" to includeEntities, *options)
    }

    fun userSuggestionCategories(lang: String? = null, vararg options: Pair<String, Any?>)= client.session.getList<UserSuggestionCategory>("/users/suggestions.json") {
        query("lang" to lang, *options)
    }

    fun userSuggestion(slug: String, lang: String? = null, vararg options: Pair<String, Any?>)= client.session.getObject<UserSuggestion>("/users/suggestions/$slug.json") {
        query("lang" to lang, *options)
    }

    fun userSuggestions(slug: String, vararg options: Pair<String, Any?>)= client.session.getList<User>("/users/suggestions/$slug/members.json") {
        query(*options)
    }

    fun reportSpam(screenName: String? = null, userId: Long? = null, performBlock: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.postObject<User>("/users/report_spam.json") {
        form("screen_name" to screenName, "user_id" to userId, "perform_block" to performBlock, *options)
    }

    @PrivateEndpoint
    fun recommendations(screenName: String? = null, userId: Long? = null, vararg options: Pair<String, Any?>)= client.session.getList<Recommendation>("/users/recommendations.json") {
        query("connections" to "true", "display_location" to "st-component", "ext" to "mediaColor", "include_entities" to "1", "include_profile_interstitial_type" to "true", "include_profile_location" to "true", "include_user_entities" to "true", "include_user_hashtag_entities" to "true", "include_user_mention_entities" to "true", "include_user_symbol_entities" to "true", "limit" to "3", "pc" to "true", "screen_name" to screenName, "user_id" to userId, *options)
    }

    @PrivateEndpoint
    fun extendedProfile(screenName: String, includeBirthdate: Boolean? = null, vararg options: Pair<String, Any?>)= client.session.getObject<ExtendedProfile>("/users/extended_profile.json") {
        query("screen_name" to screenName, "include_birthdate" to includeBirthdate, *options)
    }
}
