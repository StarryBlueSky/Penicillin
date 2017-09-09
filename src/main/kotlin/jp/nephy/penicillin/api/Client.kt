package jp.nephy.penicillin.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.api.model.*
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

class Client(private val oauth: OAuthRequestHandler) {
    fun getAccountSettings(data: Map<String,String>?=null) = "/account/settings.json".GET(oauth).getResponseObject<AccountSettings>(data)
    fun getAccountCredentials(data: Map<String,String>?=null) = "/account/verify_credentials.json".GET(oauth).getResponseObject<AccountVerifyCredentials>(data)

    fun getApplicationRateLimitStatus(data: Map<String,String>?=null) = "/application/rate_limit_status.json".GET(oauth).getResponseObject<ApplicationRateLimitStatus>(data)

    fun getBlocksIds(data: Map<String,String>?=null) = "/blocks/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getBlocksList(data: Map<String,String>?=null) = "/blocks/list.json".GET(oauth).getResponseObject<CursorUsersModel>(data)

    fun getCollectionsEntries(data: Map<String,String>?=null) = "/collections/entries.json".GET(oauth).getResponseObject<CollectionsEntries>(data)
    fun getCollectionsList(data: Map<String,String>?=null) = "/collections/list.json".GET(oauth).getResponseObject<CollectionsList>(data)
    fun getCollectionsShow(data: Map<String, String>?=null) = "/collections/show.json".GET(oauth).getResponseObject<CollectionsShow>(data)

    fun getDirectMessages(data: Map<String, String>?=null) = "/direct_messages.json".GET(oauth).getResponseList<DirectMessageModel>(data)
    fun getDirectMessagesEventsList(data: Map<String, String>?=null) = "/direct_messages/events/list.json".GET(oauth).getResponseList<DirectMessagesEventsList>(data)
    fun getDirectMessagesEventsShow(data: Map<String, String>?=null) = "/direct_messages/events/show.json".GET(oauth).getResponseObject<DirectMessagesEventsShow>(data)
    fun getDirectMessagesSent(data: Map<String, String>?=null) = "/direct_messages/sent.json".GET(oauth).getResponseList<DirectMessageModel>(data)
    fun getDirectMessagesShow(data: Map<String, String>?=null) = "/direct_messages/show.json".GET(oauth).getResponseObject<DirectMessageModel>(data)
    fun getDirectMessagesWelcomeMessagesList(data: Map<String, String>?=null) = "/direct_messages/welcome_messages/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesList>(data)
    fun getDirectMessagesWelcomeMessagesRulesList(data: Map<String, String>?=null) = "/direct_messages/welcome_messages/rules/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesRulesList>(data)
    fun getDirectMessagesWelcomeMessagesRulesShow(data: Map<String, String>?=null) = "/direct_messages/welcome_messages/rules/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesRulesShow>(data)
    fun getDirectMessagesWelcomeMessagesShow(data: Map<String, String>?=null) = "/direct_messages/welcome_messages/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesShow>(data)

    fun getFavoritesList(data: Map<String, String>?=null) = "/favorites/list.json".GET(oauth).getResponseList<TweetModel>(data)

    fun getFollowersIds(data: Map<String, String>?=null) = "/followers/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getFollowersList(data: Map<String, String>?=null) = "/followers/list.json".GET(oauth).getResponseObject<CursorUsersModel>(data)

    fun getFriendshipsIncoming(data: Map<String, String>?=null) = "/friendships/incoming.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getFriendshipsLookup(data: Map<String, String>?=null) = "/friendships/lookup.json".GET(oauth).getResponseList<FriendshipsLookup>(data)
    fun getFriendshipsNoRetweetsIds(data: Map<String, String>?=null) = "/friendships/no_retweets/ids.json".GET(oauth).getResponseObject<FriendshipsNoRetweetsIds>(data)
    fun getFriendshipsOutgoing(data: Map<String, String>?=null) = "/friendships/outgoing.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getFriendshipsShow(data: Map<String, String>?=null) = "/friendships/show.json".GET(oauth).getResponseObject<FriendshipsShow>(data)

    fun getFriendsIds(data: Map<String, String>?=null) = "/friends/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getFriendsList(data: Map<String, String>?=null) = "/friends/list.json".GET(oauth).getResponseObject<CursorUsersModel>(data)

    fun getGeoId(placeId: String, data: Map<String, String>?=null) = "/geo/id/$placeId.json".GET(oauth).getResponseObject<GeoId>(data)
    fun getGeoReverseGeocode(data: Map<String, String>?=null) = "/geo/reverse_geocode.json".GET(oauth).getResponseObject<GeoReverseGeocode>(data)
    fun getGeoSearch(data: Map<String, String>?=null) = "/geo/search.json".GET(oauth).getResponseObject<GeoSearch>(data)

    fun getHelpConfiguration(data: Map<String, String>?=null) = "/help/configuration.json".GET(oauth).getResponseObject<HelpConfiguration>(data)
    fun getHelpLanguages(data: Map<String, String>?=null) = "/help/languages.json".GET(oauth).getResponseList<HelpLanguages>(data)
    fun getHelpPrivacy(data: Map<String, String>?=null) = "/help/privacy.json".GET(oauth).getResponseObject<HelpPrivacy>(data)
    fun getHelpTos(data: Map<String, String>?=null) = "/help/tos.json".GET(oauth).getResponseObject<HelpTos>(data)

    fun getListsList(data: Map<String, String>?=null) = "/lists/list.json".GET(oauth).getResponseList<ListModel>(data)
    fun getListsMembers(data: Map<String, String>?=null) = "/lists/members.json".GET(oauth).getResponseObject<CursorUsersModel>(data)
    fun getListsMemberships(data: Map<String, String>?=null) = "/lists/memberships.json".GET(oauth).getResponseObject<CursorListsModel>(data)
    fun getListsMembersShow(data: Map<String, String>?=null) = "/lists/members/show.json".GET(oauth).getResponseObject<UserModel>(data)
    fun getListsOwnerships(data: Map<String, String>?=null) = "/lists/ownerships.json".GET(oauth).getResponseObject<CursorListsModel>(data)
    fun getListsShow(data: Map<String, String>?=null) = "/lists/show.json".GET(oauth).getResponseObject<ListModel>(data)
    fun getListsStatuses(data: Map<String, String>?=null) = "/lists/statuses.json".GET(oauth).getResponseList<TweetModel>(data)
    fun getListsSubscribers(data: Map<String, String>?=null) = "/lists/subscribers.json".GET(oauth).getResponseObject<CursorUsersModel>(data)
    fun getListsSubscribersShow(data: Map<String, String>?=null) = "/lists/subscribers/show.json".GET(oauth).getResponseObject<UserModel>(data)
    fun getListsSubscriptions(data: Map<String, String>?=null) = "/lists/subscriptions.json".GET(oauth).getResponseObject<CursorListsModel>(data)

    fun getMediaUploadStatus(mediaId: String, mediaKey: String) = "https://upload.twitter.com/1.1/media/upload.json".GET(oauth).getResponseObject<MediaUpdateStatus>(mutableMapOf<String,String>().apply {
        this["command"] = "STATUS"
        this["media_id"] = mediaId
        this["media_key"] = mediaKey
    })

    fun getMutesUsersIds(data: Map<String, String>?=null) = "/mutes/users/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getMutesUsersList(data: Map<String, String>?=null) = "/mutes/users/list.json".GET(oauth).getResponseObject<CursorUsersModel>(data)

    fun getSavedSearchesList(data: Map<String, String>?=null) = "/saved_searches/list.json".GET(oauth).getResponseList<SavedSearchModel>(data)
    fun getSavedSearchesShow(id: String, data: Map<String, String>?=null) = "/saved_searches/show/$id.json".GET(oauth).getResponseObject<SavedSearchModel>(data)

    fun getSearchTweets(data: Map<String, String>?=null) = "/search/tweets.json".GET(oauth).getResponseObject<SearchTweets>(data)

    fun getStatusesHomeTimeline(data: Map<String, String>?=null) = "/statuses/home_timeline.json".GET(oauth).getResponseList<TweetModel>(data)
    fun getStatusesLookup(data: Map<String, String>?=null) = "/statuses/lookup.json".GET(oauth).getResponseList<TweetModel>(data)
    fun getStatusesMentionsTimeline(data: Map<String, String>?=null) = "/statuses/mentions_timeline.json".GET(oauth).getResponseList<TweetModel>(data)
    fun getStatusesRetweetersIds(data: Map<String, String>?=null) = "/statuses/retweeters/ids.json".GET(oauth).getResponseObject<CursorIdsModel>(data)
    fun getStatusesRetweets(id: StatusID, data: Map<String, String>?=null) = "/statuses/retweets/$id.json".GET(oauth).getResponseList<UserModel>(data)
    fun getStatusesRetweetsOfMe(data: Map<String, String>?=null) = "/statuses/retweets_of_me.json".GET(oauth).getResponseList<TweetModel>(data)
    fun getStatusesShow(id: StatusID, data: Map<String, String>?=null) = "/statuses/show/$id.json".GET(oauth).getResponseObject<TweetModel>(data)
    fun getStatusesUserTimeline(data: Map<String, String>?=null) = "/statuses/user_timeline.json".GET(oauth).getResponseList<TweetModel>(data)

    fun getTrendsAvailable(data: Map<String, String>?=null) = "/trends/available.json".GET(oauth).getResponseList<TrendAreaModel>(data)
    fun getTrendsClosest(data: Map<String, String>?=null) = "/trends/closest.json".GET(oauth).getResponseList<TrendAreaModel>(data)
    fun getTrendsPlace(data: Map<String, String>?=null) = "/trends/place.json".GET(oauth).getResponseList<TrendModel>(data)

    fun getUsersLookup(data: Map<String, String>?=null) = "/users/lookup.json".GET(oauth).getResponseList<UserModel>(data)
    fun getUsersProfileBanner(data: Map<String, String>?=null) = "/users/profile_banner".GET(oauth).getResponseObject<UserProfileBannerModel>(data)
    fun getUsersSearch(data: Map<String, String>?=null) = "/users/search.json".GET(oauth).getResponseList<UserModel>(data)
    fun getUsersShow(data: Map<String, String>?=null) = "/users/show.json".GET(oauth).getResponseObject<UserModel>(data)
    fun getUsersSuggestions(data: Map<String, String>?=null) = "/users/suggestions.json".GET(oauth).getResponseList<UserSuggestionModel>(data)
    fun getUsersSuggestions(slug: String, data: Map<String, String>?=null) = "/users/suggestions/$slug.json".GET(oauth).getResponseObject<UserSuggestionSlugModel>(data)
    fun getUsersSuggestionsMembers(slug: String, data: Map<String, String>?=null) = "/users/suggestions/$slug/members.json".GET(oauth).getResponseList<UserModel>(data)

    fun createPollTweet(status: String, choices: List<String>, minutes: Int=1440): Any {
        val CARDS_CREATE_URL = "https://caps.twitter.com/v2/cards/create.json"
        val UPDATE_STATUS_URL = "https://api.twitter.com/1.1/statuses/update.json"

        if (status.length > 140) {
            throw IllegalArgumentException("status must have less than 140 charactors.")
        }
        if (choices.size < 2 || choices.size > 5) {
            throw IllegalArgumentException("choices must have 2, 3 or 4 Strings.")
        }
        if (minutes < 0 || minutes > 10080) {
            throw IllegalArgumentException("minutes must be in range 1..10080.")
        }

        val paramPoll = mutableMapOf<String,String>().apply {
            put("card_data", Gson().toJson(linkedMapOf<String,Any>().apply {
                choices.forEachIndexed { i, choice ->
                    put("twitter:string:choice${i + 1}_label", choice)
                }
                put("twitter:api:api:endpoint", "1")
                put("twitter:card", "poll${choices.size}choice_text_only")
                put("twitter:long:duration_minutes", minutes)
            }))
        }

        val poll = oauth.send(HTTPMethod.POST, URL(CARDS_CREATE_URL), paramPoll)
        val paramTweet = mutableMapOf<String,String>().apply {
            put("status", status)
            put("card_uri", Gson().fromJson(poll.third.component1(), JsonObject::class.java).get("card_uri").asString)
        }

        return oauth.send(HTTPMethod.POST, URL(UPDATE_STATUS_URL), paramTweet)
    }
}
