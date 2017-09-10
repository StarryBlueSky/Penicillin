package jp.nephy.penicillin.api

import com.google.gson.Gson
import jp.nephy.penicillin.api.model.*
import jp.nephy.penicillin.api.model.List
import jp.nephy.penicillin.api.result.*
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.io.File
import java.io.FileInputStream

class Client(private val oauth: OAuthRequestHandler) {
    /* Official API Start */
    fun getAccountSettings(vararg parameters: Pair<String, String>) = "/account/settings.json".GET(oauth).getResponseObject<Setting>(parameters)
    fun getAccountCredentials(vararg parameters: Pair<String, String>) = "/account/verify_credentials.json".GET(oauth).getResponseObject<AccountVerifyCredentials>(parameters)

    fun getApplicationRateLimitStatus(vararg parameters: Pair<String, String>) = "/application/rate_limit_status.json".GET(oauth).getResponseObject<ApplicationRateLimitStatus>(parameters)

    fun getBlocksIds(vararg parameters: Pair<String, String>) = "/blocks/ids.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getBlocksList(vararg parameters: Pair<String, String>) = "/blocks/list.json".GET(oauth).getResponseObject<CursorUsers>(parameters)

    fun getCollectionsEntries(vararg parameters: Pair<String, String>) = "/collections/entries.json".GET(oauth).getResponseObject<CollectionsEntries>(parameters)
    fun getCollectionsList(vararg parameters: Pair<String, String>) = "/collections/list.json".GET(oauth).getResponseObject<CollectionsList>(parameters)
    fun getCollectionsShow(vararg parameters: Pair<String, String>) = "/collections/show.json".GET(oauth).getResponseObject<CollectionsShow>(parameters)

    fun getDirectMessages(vararg parameters: Pair<String, String>) = "/direct_messages.json".GET(oauth).getResponseList<DirectMessage>(parameters)
    fun getDirectMessagesEventsList(vararg parameters: Pair<String, String>) = "/direct_messages/events/list.json".GET(oauth).getResponseList<DirectMessagesEventsList>(parameters)
    fun getDirectMessagesEventsShow(vararg parameters: Pair<String, String>) = "/direct_messages/events/show.json".GET(oauth).getResponseObject<DirectMessagesEventsShow>(parameters)
    fun getDirectMessagesSent(vararg parameters: Pair<String, String>) = "/direct_messages/sent.json".GET(oauth).getResponseList<DirectMessage>(parameters)
    fun getDirectMessagesShow(vararg parameters: Pair<String, String>) = "/direct_messages/show.json".GET(oauth).getResponseObject<DirectMessage>(parameters)
    fun getDirectMessagesWelcomeMessagesList(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesList>(parameters)
    fun getDirectMessagesWelcomeMessagesRulesList(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/list.json".GET(oauth).getResponseList<DirectMessagesWelcomeMessagesRulesList>(parameters)
    fun getDirectMessagesWelcomeMessagesRulesShow(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesRulesShow>(parameters)
    fun getDirectMessagesWelcomeMessagesShow(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/show.json".GET(oauth).getResponseObject<DirectMessagesWelcomeMessagesShow>(parameters)

    fun getFavoritesList(vararg parameters: Pair<String, String>) = "/favorites/list.json".GET(oauth).getResponseList<Status>(parameters)

    fun getFollowersIds(vararg parameters: Pair<String, String>) = "/followers/ids.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getFollowersList(vararg parameters: Pair<String, String>) = "/followers/list.json".GET(oauth).getResponseObject<CursorUsers>(parameters)

    fun getFriendshipsIncoming(vararg parameters: Pair<String, String>) = "/friendships/incoming.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getFriendshipsLookup(vararg parameters: Pair<String, String>) = "/friendships/lookup.json".GET(oauth).getResponseList<FriendshipsLookup>(parameters)
    fun getFriendshipsNoRetweetsIds(vararg parameters: Pair<String, String>) = "/friendships/no_retweets/ids.json".GET(oauth).getResponseObject<FriendshipsNoRetweetsIds>(parameters)
    fun getFriendshipsOutgoing(vararg parameters: Pair<String, String>) = "/friendships/outgoing.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getFriendshipsShow(vararg parameters: Pair<String, String>) = "/friendships/show.json".GET(oauth).getResponseObject<FriendshipsShow>(parameters)

    fun getFriendsIds(vararg parameters: Pair<String, String>) = "/friends/ids.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getFriendsList(vararg parameters: Pair<String, String>) = "/friends/list.json".GET(oauth).getResponseObject<CursorUsers>(parameters)

    fun getGeoId(placeId: String, vararg parameters: Pair<String, String>) = "/geo/id/$placeId.json".GET(oauth).getResponseObject<Place>(parameters)
    fun getGeoReverseGeocode(vararg parameters: Pair<String, String>) = "/geo/reverse_geocode.json".GET(oauth).getResponseObject<GeoReverseGeocode>(parameters)
    fun getGeoSearch(vararg parameters: Pair<String, String>) = "/geo/search.json".GET(oauth).getResponseObject<GeoSearch>(parameters)

    fun getHelpConfiguration(vararg parameters: Pair<String, String>) = "/help/configuration.json".GET(oauth).getResponseObject<Configuration>(parameters)
    fun getHelpLanguages(vararg parameters: Pair<String, String>) = "/help/languages.json".GET(oauth).getResponseList<Language>(parameters)
    fun getHelpPrivacy(vararg parameters: Pair<String, String>) = "/help/privacy.json".GET(oauth).getResponseObject<Privacy>(parameters)
    fun getHelpTos(vararg parameters: Pair<String, String>) = "/help/tos.json".GET(oauth).getResponseObject<Tos>(parameters)

    fun getListsList(vararg parameters: Pair<String, String>) = "/lists/list.json".GET(oauth).getResponseList<List>(parameters)
    fun getListsMembers(vararg parameters: Pair<String, String>) = "/lists/members.json".GET(oauth).getResponseObject<CursorUsers>(parameters)
    fun getListsMemberships(vararg parameters: Pair<String, String>) = "/lists/memberships.json".GET(oauth).getResponseObject<CursorLists>(parameters)
    fun getListsMembersShow(vararg parameters: Pair<String, String>) = "/lists/members/show.json".GET(oauth).getResponseObject<User>(parameters)
    fun getListsOwnerships(vararg parameters: Pair<String, String>) = "/lists/ownerships.json".GET(oauth).getResponseObject<CursorLists>(parameters)
    fun getListsShow(vararg parameters: Pair<String, String>) = "/lists/show.json".GET(oauth).getResponseObject<List>(parameters)
    fun getListsStatuses(vararg parameters: Pair<String, String>) = "/lists/statuses.json".GET(oauth).getResponseList<Status>(parameters)
    fun getListsSubscribers(vararg parameters: Pair<String, String>) = "/lists/subscribers.json".GET(oauth).getResponseObject<CursorUsers>(parameters)
    fun getListsSubscribersShow(vararg parameters: Pair<String, String>) = "/lists/subscribers/show.json".GET(oauth).getResponseObject<User>(parameters)
    fun getListsSubscriptions(vararg parameters: Pair<String, String>) = "/lists/subscriptions.json".GET(oauth).getResponseObject<CursorLists>(parameters)

    fun getMediaUploadStatus(mediaId: String, mediaKey: String) = "https://upload.twitter.com/1.1/media/upload.json".GET(oauth).getResponseObject<Media>(
            arrayOf("command" to "STATUS", "media_id" to mediaId, "media_key" to mediaKey)
    )

    fun getMutesUsersIds(vararg parameters: Pair<String, String>) = "/mutes/users/ids.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getMutesUsersList(vararg parameters: Pair<String, String>) = "/mutes/users/list.json".GET(oauth).getResponseObject<CursorUsers>(parameters)

    fun getSavedSearchesList(vararg parameters: Pair<String, String>) = "/saved_searches/list.json".GET(oauth).getResponseList<SavedSearch>(parameters)
    fun getSavedSearchesShow(id: String, vararg parameters: Pair<String, String>) = "/saved_searches/show/$id.json".GET(oauth).getResponseObject<SavedSearch>(parameters)

    fun getSearchTweets(vararg parameters: Pair<String, String>) = "/search/tweets.json".GET(oauth).getResponseObject<Search>(parameters)

    fun getStatusesHomeTimeline(vararg parameters: Pair<String, String>) = "/statuses/home_timeline.json".GET(oauth).getResponseList<Status>(parameters)
    fun getStatusesLookup(vararg parameters: Pair<String, String>) = "/statuses/lookup.json".GET(oauth).getResponseList<Status>(parameters)
    fun getStatusesMentionsTimeline(vararg parameters: Pair<String, String>) = "/statuses/mentions_timeline.json".GET(oauth).getResponseList<Status>(parameters)
    fun getStatusesRetweetersIds(vararg parameters: Pair<String, String>) = "/statuses/retweeters/ids.json".GET(oauth).getResponseObject<CursorIds>(parameters)
    fun getStatusesRetweets(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/retweets/$id.json".GET(oauth).getResponseList<User>(parameters)
    fun getStatusesRetweetsOfMe(vararg parameters: Pair<String, String>) = "/statuses/retweets_of_me.json".GET(oauth).getResponseList<Status>(parameters)
    fun getStatusesShow(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/show/$id.json".GET(oauth).getResponseObject<Status>(parameters)
    fun getStatusesUserTimeline(vararg parameters: Pair<String, String>) = "/statuses/user_timeline.json".GET(oauth).getResponseList<Status>(parameters)

    fun getTrendsAvailable(vararg parameters: Pair<String, String>) = "/trends/available.json".GET(oauth).getResponseList<TrendArea>(parameters)
    fun getTrendsClosest(vararg parameters: Pair<String, String>) = "/trends/closest.json".GET(oauth).getResponseList<TrendArea>(parameters)
    fun getTrendsPlace(vararg parameters: Pair<String, String>) = "/trends/place.json".GET(oauth).getResponseList<TrendsPlace>(parameters)

    fun getUsersLookup(vararg parameters: Pair<String, String>) = "/users/lookup.json".GET(oauth).getResponseList<User>(parameters)
    fun getUsersProfileBanner(vararg parameters: Pair<String, String>) = "/users/profile_banner".GET(oauth).getResponseObject<UserProfileBannerModel>(parameters)
    fun getUsersSearch(vararg parameters: Pair<String, String>) = "/users/search.json".GET(oauth).getResponseList<User>(parameters)
    fun getUsersShow(vararg parameters: Pair<String, String>) = "/users/show.json".GET(oauth).getResponseObject<User>(parameters)
    fun getUsersSuggestions(vararg parameters: Pair<String, String>) = "/users/suggestions.json".GET(oauth).getResponseList<UserSuggestionModel>(parameters)
    fun getUsersSuggestions(slug: String, vararg parameters: Pair<String, String>) = "/users/suggestions/$slug.json".GET(oauth).getResponseObject<UserSuggestionSlugModel>(parameters)
    fun getUsersSuggestionsMembers(slug: String, vararg parameters: Pair<String, String>) = "/users/suggestions/$slug/members.json".GET(oauth).getResponseList<User>(parameters)

    fun postAccountRemoveProfileBanner(vararg parameters: Pair<String, String>) = "/account/remove_profile_banner.json".POST(oauth).getResponseObject<User>(parameters)
    fun postAccountSettings(vararg parameters: Pair<String, String>) = "/account/settings.json".POST(oauth).getResponseObject<Setting>(parameters)
    fun postAccountUpdateProfile(vararg parameters: Pair<String, String>) = "/account/update_profile.json".POST(oauth).getResponseObject<User>(parameters)
    fun postAccountUpdateProfileBackgroundImage(vararg parameters: Pair<String, String>) = "/account/update_profile_background_image.json".POST(oauth).getResponseObject<User>(parameters)
    fun postAccountUpdateProfileBanner(vararg parameters: Pair<String, String>) = "/account/update_profile_banner.json".POST(oauth).getResponseObject<User>(parameters)
    fun postAccountUpdateProfileImage(vararg parameters: Pair<String, String>) = "/account/update_profile_image.json".POST(oauth).getResponseObject<User>(parameters)

    fun postBlocksCreate(vararg parameters: Pair<String, String>) = "/blocks/create.json".POST(oauth).getResponseObject<User>(parameters)
    fun postBlocksDestroy(vararg parameters: Pair<String, String>) = "/blocks/destroy.json".POST(oauth).getResponseObject<User>(parameters)

    fun postFavoritesCreate(vararg parameters: Pair<String,String>) = "/favorites/create.json".POST(oauth).getResponseObject<Status>(parameters)
    fun postFavoritesDestroy(vararg parameters: Pair<String, String>) = "/favorites/destroy.json".POST(oauth).getResponseObject<Status>(parameters)

    fun postFriendshipsCreate(vararg parameters: Pair<String, String>) = "/friendships/create.json".POST(oauth).getResponseObject<User>(parameters)
    fun postFriendshipsDestroy(vararg parameters: Pair<String, String>) = "/friendships/destroy.json".POST(oauth).getResponseObject<User>(parameters)
    fun postFriendshipsUpdate(vararg parameters: Pair<String, String>) = "/friendships/update.json".POST(oauth).getResponseObject<User>(parameters)

    fun postListsCreate(vararg parameters: Pair<String, String>) = "/lists/create.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsDestroy(vararg parameters: Pair<String, String>) = "/lists/destroy.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsMembersCreate(vararg parameters: Pair<String, String>) = "/lists/members/create.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsMembersCreateAll(vararg parameters: Pair<String, String>) = "/lists/members/create_all.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsMembersDestroy(vararg parameters: Pair<String, String>) = "/lists/members/destroy.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsMembersDestroyAll(vararg parameters: Pair<String, String>) = "/lists/members/destroy_all.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsSubscribersCreate(vararg parameters: Pair<String, String>) = "/lists/subscribers/create.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsSubscribersDestroy(vararg parameters: Pair<String, String>) = "/lists/subscribers/destroy.json".POST(oauth).getResponseObject<List>(parameters)
    fun postListsUpdate(vararg parameters: Pair<String, String>) = "/lists/update.json".POST(oauth).getResponseObject<List>(parameters)

    fun postMediaUpload(vararg parameters: Pair<String, String>) = "https://upload.twitter.com/1.1/media/upload.json".POST(oauth).getResponseObject<Media>(parameters)
    fun postMediaUploadBytes(file: ByteArray, vararg parameters: Pair<String, String>) = "https://upload.twitter.com/1.1/media/upload.json".POST(oauth).getResponseObject<Media>(parameters, file)

    fun postMutesUsersCreate(vararg parameters: Pair<String, String>) = "/mutes/users/create.json".POST(oauth).getResponseObject<User>(parameters)
    fun postMutesUsersDestroy(vararg parameters: Pair<String, String>) = "/mutes/users/destroy.json".POST(oauth).getResponseObject<User>(parameters)

    fun postSavedSearchesCreate(vararg parameters: Pair<String, String>) = "/saved_searches/create.json".POST(oauth).getResponseObject<SavedSearch>(parameters)
    fun postSavedSearchesDestroy(id: String, vararg parameters: Pair<String, String>) = "/saved_searches/destroy/$id.json".POST(oauth).getResponseObject<SavedSearch>(parameters)

    fun postStatusesDestroy(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/destroy/$id.json".POST(oauth).getResponseObject<Status>(parameters)
    fun postStatusesRetweet(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/retweet/$id.json".POST(oauth).getResponseObject<Status>(parameters)
    fun postStatusesUnretweet(id: StatusID, vararg parameters: Pair<String, String>) = "/statuses/unretweet/$id.json".POST(oauth).getResponseObject<Status>(parameters)
    fun postStatusesUpdate(vararg parameters: Pair<String, String>) = "/statuses/update.json".POST(oauth).getResponseObject<Status>(parameters)

    fun postUsersReportSpam(vararg parameters: Pair<String, String>) = "/users/report_spam.json".POST(oauth).getResponseObject<User>(parameters)

    fun deleteDirectMessagesWelcomeMessagesDestroy(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/destroy.json".DELETE(oauth).getResponseObject<DirectMessagesWelcomeMessagesShow>(parameters)
    fun deleteDirectMessagesWelcomeMessagesRulesDestroy(vararg parameters: Pair<String, String>) = "/direct_messages/welcome_messages/rules/destroy.json".DELETE(oauth).getResponseObject<DirectMessagesWelcomeMessagesRulesShow>(parameters)
    /* Official API End */


    /* Unofficial API Start */
    fun postCardsCreate(vararg parameters: Pair<String, String>) = "https://caps.twitter.com/v2/cards/create.json".POST(oauth).getResponseObject<Card>(parameters)
    /* Unofficial API End */


    /* API Mnemonics Start */
    fun updateStatus(vararg parameters: Pair<String, String>) = postStatusesUpdate(*parameters)

    fun updateStatusWithMedia(media: Array<Pair<File, String>>, vararg parameters: Pair<String, String>): ResponseObject<Status?> {
        val maxSeparateByte = 5 * 1024 * 1024
        val mediaIds = mutableListOf<String>()

        media.forEach {
            val totalBytes = it.first.length()

            val initResult = postMediaUpload("command" to "INIT", "media_type" to it.second, "total_bytes" to totalBytes.toString())
            val separateCount = totalBytes / maxSeparateByte + (if (totalBytes % maxSeparateByte > 0) 1 else 0)

            val stream = FileInputStream(it.first)
            for (i in 0 until separateCount) {
                val startByte = i * maxSeparateByte
                val size = if ((i + 1) * maxSeparateByte <= totalBytes) maxSeparateByte else (totalBytes - startByte).toInt()
                val data = ByteArray(size)

                stream.read(data)

                postMediaUploadBytes(data, "command" to "APPEND", "media_id" to initResult.data!!.mediaIdString, "segment_index" to i.toString()).print()
            }
            postMediaUpload("command" to "FINALIZE", "media_id" to initResult.data!!.mediaIdString)
            mediaIds.add(initResult.data.mediaIdString)
        }

        return postStatusesUpdate(*parameters, "media_ids" to mediaIds.joinToString(","))
    }

    fun createPollTweet(status: String, choices: Array<String>, minutes: Int=1440): ResponseObject<Status?> {
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

        return postStatusesUpdate("status" to status, "card_uri" to result.data!!.cardUri)
    }
    /* API Mnemonics End */
}
