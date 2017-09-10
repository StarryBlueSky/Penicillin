package jp.nephy.penicillin.api

import com.google.gson.Gson
import jp.nephy.penicillin.api.model.*
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class Client(private val oauth: OAuthRequestHandler) {
    /* Official API Start */
    fun getAccountSettings(vararg parameters: Pair<String, String>) = "/account/settings.json".GET(oauth).getResponseObject<AccountSettings>(parameters)
    fun getAccountCredentials(vararg parameters: Pair<String, String>) = "/account/verify_credentials.json".GET(oauth).getResponseObject<UserModel>(parameters)

    fun getApplicationRateLimitStatus(vararg parameters: Pair<String, String>) = "/application/rate_limit_status.json".GET(oauth).getResponseObject<ApplicationRateLimitStatus>(parameters)

    fun getBlocksIds(vararg parameters: Pair<String, String>) = "/blocks/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getBlocksList(vararg parameters: Pair<String, String>) = "/blocks/list.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)

    fun getCollectionsEntries(vararg parameters: Pair<String, String>) = "/collections/entries.json".GET(oauth).getResponseObject<CollectionsEntries>(parameters)
    fun getCollectionsList(vararg parameters: Pair<String, String>) = "/collections/list.json".GET(oauth).getResponseObject<CollectionsList>(parameters)
    fun getCollectionsShow(vararg parameters: Pair<String, String>) = "/collections/show.json".GET(oauth).getResponseObject<CollectionsShow>(parameters)

    fun getDirectMessages(vararg parameters: Pair<String, String>) = "/direct_messages.json".GET(oauth).getResponseList<DirectMessageModel>(parameters)
    fun getDirectMessagesEventsList(vararg parameters: Pair<String, String>) = "/direct_messages/events/list.json".GET(oauth).getResponseList<DirectMessagesEventsList>(parameters)
    fun getDirectMessagesEventsShow(vararg parameters: Pair<String, String>) = "/direct_messages/events/show.json".GET(oauth).getResponseObject<DirectMessagesEventsShow>(parameters)
    fun getDirectMessagesSent(vararg parameters: Pair<String, String>) = "/direct_messages/sent.json".GET(oauth).getResponseList<DirectMessageModel>(parameters)
    fun getDirectMessagesShow(vararg parameters: Pair<String, String>) = "/direct_messages/show.json".GET(oauth).getResponseObject<DirectMessageModel>(parameters)
    fun getDirectMessagesWelcomeMessagesList(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesList>(parameters)
    fun getDirectMessagesWelcomeMessagesRulesList(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesRulesList>(parameters)
    fun getDirectMessagesWelcomeMessagesRulesShow(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesRulesShow>(parameters)
    fun getDirectMessagesWelcomeMessagesShow(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesShow>(parameters)

    fun getFavoritesList(vararg parameters: Pair<String, String>) = "/favorites/list.json".GET(oauth).getResponseList<TweetModel>(parameters)

    fun getFollowersIds(vararg parameters: Pair<String, String>) = "/followers/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getFollowersList(vararg parameters: Pair<String, String>) = "/followers/list.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)

    fun getFriendshipsIncoming(vararg parameters: Pair<String, String>) = "/friendships/incoming.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getFriendshipsLookup(vararg parameters: Pair<String, String>) = "/friendships/lookup.json".GET(oauth).getResponseList<FriendshipsLookup>(parameters)
    fun getFriendshipsNoRetweetsIds(vararg parameters: Pair<String, String>) = "/friendships/no_retweets/ids.json".GET(oauth).getResponseObject<FriendshipsNoRetweetsIds>(parameters)
    fun getFriendshipsOutgoing(vararg parameters: Pair<String, String>) = "/friendships/outgoing.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getFriendshipsShow(vararg parameters: Pair<String, String>) = "/friendships/show.json".GET(oauth).getResponseObject<FriendshipsShow>(parameters)

    fun getFriendsIds(vararg parameters: Pair<String, String>) = "/friends/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getFriendsList(vararg parameters: Pair<String, String>) = "/friends/list.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)

    fun getGeoId(placeId: String, vararg parameters: Pair<String, String>) = "/geo/id/$placeId.json".GET(oauth).getResponseObject<GeoId>(parameters)
    fun getGeoReverseGeocode(vararg parameters: Pair<String, String>) = "/geo/reverse_geocode.json".GET(oauth).getResponseObject<GeoReverseGeocode>(parameters)
    fun getGeoSearch(vararg parameters: Pair<String, String>) = "/geo/search.json".GET(oauth).getResponseObject<GeoSearch>(parameters)

    fun getHelpConfiguration(vararg parameters: Pair<String, String>) = "/help/configuration.json".GET(oauth).getResponseObject<HelpConfiguration>(parameters)
    fun getHelpLanguages(vararg parameters: Pair<String, String>) = "/help/languages.json".GET(oauth).getResponseList<HelpLanguages>(parameters)
    fun getHelpPrivacy(vararg parameters: Pair<String, String>) = "/help/privacy.json".GET(oauth).getResponseObject<HelpPrivacy>(parameters)
    fun getHelpTos(vararg parameters: Pair<String, String>) = "/help/tos.json".GET(oauth).getResponseObject<HelpTos>(parameters)

    fun getListsList(vararg parameters: Pair<String, String>) = "/lists/list.json".GET(oauth).getResponseList<ListModel>(parameters)
    fun getListsMembers(vararg parameters: Pair<String, String>) = "/lists/members.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)
    fun getListsMemberships(vararg parameters: Pair<String, String>) = "/lists/memberships.json".GET(oauth).getResponseObject<CursorListsModel>(parameters)
    fun getListsMembersShow(vararg parameters: Pair<String, String>) = "/lists/members/show.json".GET(oauth).getResponseObject<UserModel>(parameters)
    fun getListsOwnerships(vararg parameters: Pair<String, String>) = "/lists/ownerships.json".GET(oauth).getResponseObject<CursorListsModel>(parameters)
    fun getListsShow(vararg parameters: Pair<String, String>) = "/lists/show.json".GET(oauth).getResponseObject<ListModel>(parameters)
    fun getListsStatuses(vararg parameters: Pair<String, String>) = "/lists/statuses.json".GET(oauth).getResponseList<TweetModel>(parameters)
    fun getListsSubscribers(vararg parameters: Pair<String, String>) = "/lists/subscribers.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)
    fun getListsSubscribersShow(vararg parameters: Pair<String, String>) = "/lists/subscribers/show.json".GET(oauth).getResponseObject<UserModel>(parameters)
    fun getListsSubscriptions(vararg parameters: Pair<String, String>) = "/lists/subscriptions.json".GET(oauth).getResponseObject<CursorListsModel>(parameters)

    fun getMediaUploadStatus(mediaId: String, mediaKey: String) = "https://upload.twitter.com/1.1/media/upload.json".GET(oauth).getResponseObject<MediaUpdateStatus>(
            arrayOf("command" to "STATUS", "media_id" to mediaId, "media_key" to mediaKey)
    )

    fun getMutesUsersIds(vararg parameters: Pair<String, String>) = "/mutes/users/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getMutesUsersList(vararg parameters: Pair<String, String>) = "/mutes/users/list.json".GET(oauth).getResponseObject<CursorUsersModel>(parameters)

    fun getSavedSearchesList(vararg parameters: Pair<String, String>) = "/saved_searches/list.json".GET(oauth).getResponseList<SavedSearchModel>(parameters)
    fun getSavedSearchesShow(id: String, vararg parameters: Pair<String, String>) = "/saved_searches/show/$id.json".GET(oauth).getResponseObject<SavedSearchModel>(parameters)

    fun getSearchTweets(vararg parameters: Pair<String, String>) = "/search/tweets.json".GET(oauth).getResponseObject<SearchTweets>(parameters)

    fun getStatusesHomeTimeline(vararg parameters: Pair<String, String>) = "/statuses/home_timeline.json".GET(oauth).getResponseList<TweetModel>(parameters)
    fun getStatusesLookup(vararg parameters: Pair<String, String>) = "/statuses/lookup.json".GET(oauth).getResponseList<TweetModel>(parameters)
    fun getStatusesMentionsTimeline(vararg parameters: Pair<String, String>) = "/statuses/mentions_timeline.json".GET(oauth).getResponseList<TweetModel>(parameters)
    fun getStatusesRetweetersIds(vararg parameters: Pair<String, String>) = "/statuses/retweeters/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(parameters)
    fun getStatusesRetweets(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/retweets/$id.json".GET(oauth).getResponseList<UserModel>(parameters)
    fun getStatusesRetweetsOfMe(vararg parameters: Pair<String, String>) = "/statuses/retweets_of_me.json".GET(oauth).getResponseList<TweetModel>(parameters)
    fun getStatusesShow(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/show/$id.json".GET(oauth).getResponseObject<TweetModel>(parameters)
    fun getStatusesUserTimeline(vararg parameters: Pair<String, String>) = "/statuses/user_timeline.json".GET(oauth).getResponseList<TweetModel>(parameters)

    fun getTrendsAvailable(vararg parameters: Pair<String, String>) = "/trends/available.json".GET(oauth).getResponseList<TrendAreaModel>(parameters)
    fun getTrendsClosest(vararg parameters: Pair<String, String>) = "/trends/closest.json".GET(oauth).getResponseList<TrendAreaModel>(parameters)
    fun getTrendsPlace(vararg parameters: Pair<String, String>) = "/trends/place.json".GET(oauth).getResponseList<TrendModel>(parameters)

    fun getUsersLookup(vararg parameters: Pair<String, String>) = "/users/lookup.json".GET(oauth).getResponseList<UserModel>(parameters)
    fun getUsersProfileBanner(vararg parameters: Pair<String, String>) = "/users/profile_banner".GET(oauth).getResponseObject<UserProfileBannerModel>(parameters)
    fun getUsersSearch(vararg parameters: Pair<String, String>) = "/users/search.json".GET(oauth).getResponseList<UserModel>(parameters)
    fun getUsersShow(vararg parameters: Pair<String, String>) = "/users/show.json".GET(oauth).getResponseObject<UserModel>(parameters)
    fun getUsersSuggestions(vararg parameters: Pair<String, String>) = "/users/suggestions.json".GET(oauth).getResponseList<UserSuggestionModel>(parameters)
    fun getUsersSuggestions(slug: String, vararg parameters: Pair<String, String>) = "/users/suggestions/$slug.json".GET(oauth).getResponseObject<UserSuggestionSlugModel>(parameters)
    fun getUsersSuggestionsMembers(slug: String, vararg parameters: Pair<String, String>) = "/users/suggestions/$slug/members.json".GET(oauth).getResponseList<UserModel>(parameters)

    fun postAccountRemoveProfileBanner(vararg parameters: Pair<String, String>) = "/account/remove_profile_banner.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postAccountSettings(vararg parameters: Pair<String, String>) = "/account/settings.json".POST(oauth).getResponseObject<AccountSettings>(parameters)
    fun postAccountUpdateProfile(vararg parameters: Pair<String, String>) = "/account/update_profile.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postAccountUpdateProfileBackgroundImage(vararg parameters: Pair<String, String>) = "/account/update_profile_background_image.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postAccountUpdateProfileBanner(vararg parameters: Pair<String, String>) = "/account/update_profile_banner.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postAccountUpdateProfileImage(vararg parameters: Pair<String, String>) = "/account/update_profile_image.json".POST(oauth).getResponseObject<UserModel>(parameters)

    fun postBlocksCreate(vararg parameters: Pair<String, String>) = "/blocks/create.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postBlocksDestroy(vararg parameters: Pair<String, String>) = "/blocks/destroy.json".POST(oauth).getResponseObject<UserModel>(parameters)

    fun postFavoritesCreate(vararg parameters: Pair<String,String>) = "/favorites/create.json".POST(oauth).getResponseObject<TweetModel>(parameters)
    fun postFavoritesDestroy(vararg parameters: Pair<String, String>) = "/favorites/destroy.json".POST(oauth).getResponseObject<TweetModel>(parameters)

    fun postFriendshipsCreate(vararg parameters: Pair<String, String>) = "/friendships/create.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postFriendshipsDestroy(vararg parameters: Pair<String, String>) = "/friendships/destroy.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postFriendshipsUpdate(vararg parameters: Pair<String, String>) = "/friendships/update.json".POST(oauth).getResponseObject<UserModel>(parameters)

    fun postListsCreate(vararg parameters: Pair<String, String>) = "/lists/create.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsDestroy(vararg parameters: Pair<String, String>) = "/lists/destroy.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsMembersCreate(vararg parameters: Pair<String, String>) = "/lists/members/create.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsMembersCreateAll(vararg parameters: Pair<String, String>) = "/lists/members/create_all.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsMembersDestroy(vararg parameters: Pair<String, String>) = "/lists/members/destroy.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsMembersDestroyAll(vararg parameters: Pair<String, String>) = "/lists/members/destroy_all.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsSubscribersCreate(vararg parameters: Pair<String, String>) = "/lists/subscribers/create.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsSubscribersDestroy(vararg parameters: Pair<String, String>) = "/lists/subscribers/destroy.json".POST(oauth).getResponseObject<ListModel>(parameters)
    fun postListsUpdate(vararg parameters: Pair<String, String>) = "/lists/update.json".POST(oauth).getResponseObject<ListModel>(parameters)

    fun postMutesUsersCreate(vararg parameters: Pair<String, String>) = "/mutes/users/create.json".POST(oauth).getResponseObject<UserModel>(parameters)
    fun postMutesUsersDestroy(vararg parameters: Pair<String, String>) = "/mutes/users/destroy.json".POST(oauth).getResponseObject<UserModel>(parameters)

    fun postSavedSearchesCreate(vararg parameters: Pair<String, String>) = "/saved_searches/create.json".POST(oauth).getResponseObject<SavedSearchModel>(parameters)
    fun postSavedSearchesDestroy(id: String, vararg parameters: Pair<String, String>) = "/saved_searches/destroy/$id.json".POST(oauth).getResponseObject<SavedSearchModel>(parameters)

    fun postStatusesDestroy(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/destroy/$id.json".POST(oauth).getResponseObject<TweetModel>(parameters)
    fun postStatusesRetweet(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/retweet/$id.json".POST(oauth).getResponseObject<TweetModel>(parameters)
    fun postStatusesUnretweet(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/unretweet/$id.json".POST(oauth).getResponseObject<TweetModel>(parameters)
    fun postStatusesUpdate(vararg parameters: Pair<String, String>) = "/statuses/update.json".POST(oauth).getResponseObject<TweetModel>(parameters)

    fun postUsersReportSpam(vararg parameters: Pair<String, String>) = "/users/report_spam.json".POST(oauth).getResponseObject<UserModel>(parameters)

    fun deleteDirectMessagesWelcomeMessagesDestroy(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/destroy.json".DELETE(oauth).getResponseObject<DirectMessagesWelcomeMessagesShow>(parameters)
    fun deleteDirectMessagesWelcomeMessagesRulesDestroy(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/destroy.json".DELETE(oauth).getResponseObject<DirectMessagesWelcomeMessagesRulesShow>(parameters)
    /* Official API End */


    /* Unofficial API Start */
    fun postCardsCreate(vararg parameters: Pair<String, String>) = "https://caps.twitter.com/v2/cards/create.json".POST(oauth).getResponseObject<CardCreateModel>(parameters)
    /* Unofficial API End */


    /* API Mnemonics Start */
    fun createPollTweet(status: String, choices: Array<String>, minutes: Int=1440): ResponseObject<TweetModel> {
        if (status.length > 140) {
            throw IllegalArgumentException("status must have less than 140 charactors.")
        }
        if (choices.size < 2 || choices.size > 5) {
            throw IllegalArgumentException("choices must have 2, 3 or 4 Strings.")
        }
        if (minutes < 0 || minutes > 10080) {
            throw IllegalArgumentException("minutes must be in range 1..10080.")
        }

        val result = postCardsCreate("card_data" to Gson().toJson(linkedMapOf<String,Any>().apply {
            choices.forEachIndexed { i, choice ->
                put("twitter:string:choice${i + 1}_label", choice)
            }
            put("twitter:api:api:endpoint", "1")
            put("twitter:card", "poll${choices.size}choice_text_only")
            put("twitter:long:duration_minutes", minutes)
        }))

        return postStatusesUpdate("status" to status, "card_uri" to result.data.cardUri)
    }
    /* API Mnemonics End */
}
