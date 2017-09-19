package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class Resources(val json: JsonElement) {
    val accountLoginVerificationEnrollment by json["account"].byModel<ApplicationRateLimit?>("/account/login_verification_enrollment")
    val accountLoginVerificationEnrollmentV2 by json["account"].byModel<ApplicationRateLimit?>("/account/login_verification_enrollment_v2")
    val accountLoginVerificationRequest by json["account"].byModel<ApplicationRateLimit?>("/account/login_verification_request")
    val accountPersonalizationAppGraph by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/app_graph")
    val accountPersonalizationAppgraphOptoutStatus by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/appgraph/optout_status")
    val accountPersonalizationDownloadAdvertiserList by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/download_advertiser_list")
    val accountPersonalizationDownloadYourData by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/download_your_data")
    val accountPersonalizationP13NData by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/p13n_data")
    val accountPersonalizationP13NPreferences by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/p13n_preferences")
    val accountPersonalizationPartnerInterests by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/partner_interests")
    val accountPersonalizationSetOptoutCookies by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/set_optout_cookies")
    val accountPersonalizationTwitterInterests by json["account"].byModel<ApplicationRateLimit?>("/account/personalization/twitter_interests")
    val accountSettings by json["account"].byModel<ApplicationRateLimit?>("/account/settings")
    val accountUpdateProfile by json["account"].byModel<ApplicationRateLimit?>("/account/update_profile")
    val accountVerification by json["account"].byModel<ApplicationRateLimit?>("/account/verification")
    val accountVerifyCredentials by json["account"].byModel<ApplicationRateLimit?>("/account/verify_credentials")
    val accountActivityWebhooks by json["account_activity"].byModel<ApplicationRateLimit?>("/account_activity/webhooks")
    val accountActivityWebhooksIdSubscriptions by json["account_activity"].byModel<ApplicationRateLimit?>("/account_activity/webhooks/:id/subscriptions")
    val accountActivityWebhooksIdSubscriptionsAll by json["account_activity"].byModel<ApplicationRateLimit?>("/account_activity/webhooks/:id/subscriptions/all")
    val accountActivityWebhooksIdSubscriptionsDirectMessages by json["account_activity"].byModel<ApplicationRateLimit?>("/account_activity/webhooks/:id/subscriptions/direct_messages")
    val activityAboutMe by json["activity"].byModel<ApplicationRateLimit?>("/activity/about_me")
    val activityAboutMeUnread by json["activity"].byModel<ApplicationRateLimit?>("/activity/about_me/unread")
    val activityByFriends by json["activity"].byModel<ApplicationRateLimit?>("/activity/by_friends")
    val activityDs by json["activity"].byModel<ApplicationRateLimit?>("/activity/ds")
    val adaptive by json["adaptive"].byModel<ApplicationRateLimit?>("/adaptive")
    val adsCampaignsAccountPermissions by json["ads"].byModel<ApplicationRateLimit?>("/ads/campaigns/account_permissions")
    val alertsLandingPage by json["alerts"].byModel<ApplicationRateLimit?>("/alerts/landing_page")
    val amplifyCategories by json["amplify"].byModel<ApplicationRateLimit?>("/amplify/categories")
    val amplifyMarketplaceDefaults by json["amplify"].byModel<ApplicationRateLimit?>("/amplify/marketplace/defaults")
    val amplifyMarketplaceVideos by json["amplify"].byModel<ApplicationRateLimit?>("/amplify/marketplace/videos")
    val applicationRateLimitStatus by json["application"].byModel<ApplicationRateLimit?>("/application/rate_limit_status")
    val authCsrfToken by json["auth"].byModel<ApplicationRateLimit?>("/auth/csrf_token")
    val betaTimelinesCustomList by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/custom/list")
    val betaTimelinesCustomShow by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/custom/show")
    val betaTimelinesCustomWhitelisted by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/custom/whitelisted")
    val betaTimelinesFollow by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/follow")
    val betaTimelinesTimeline by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/timeline")
    val betaTimelinesUnfollow by json["beta"].byModel<ApplicationRateLimit?>("/beta/timelines/unfollow")
    val blocksExists by json["blocks"].byModel<ApplicationRateLimit?>("/blocks/exists")
    val blocksIds by json["blocks"].byModel<ApplicationRateLimit?>("/blocks/ids")
    val blocksList by json["blocks"].byModel<ApplicationRateLimit?>("/blocks/list")
    val broadcastsShow by json["broadcasts"].byModel<ApplicationRateLimit?>("/broadcasts/show")
    val businessExperienceAnalyticsAccount by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/analytics/account")
    val businessExperienceAnalyticsTweets by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/analytics/tweets")
    val businessExperienceDashboardFeatures by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/dashboard_features")
    val businessExperienceDashboardSettingsDestroy by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/dashboard_settings/destroy")
    val businessExperienceDashboardSettingsShow by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/dashboard_settings/show")
    val businessExperienceDashboardSettingsUpdate by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/dashboard_settings/update")
    val businessExperienceInboxInteractions by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/inbox/interactions")
    val businessExperienceInboxShow by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/inbox/show")
    val businessExperienceKeywords by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/keywords")
    val businessExperienceTwitterProSettingsDestroy by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/twitter_pro_settings/destroy")
    val businessExperienceTwitterProSettingsShow by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/twitter_pro_settings/show")
    val businessExperienceTwitterProSettingsUpdate by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/twitter_pro_settings/update")
    val businessExperienceUserFeatures by json["business_experience"].byModel<ApplicationRateLimit?>("/business_experience/user_features")
    val businessProfilesShow by json["business_profiles"].byModel<ApplicationRateLimit?>("/business_profiles/show")
    val businessProfilesUpdate by json["business_profiles"].byModel<ApplicationRateLimit?>("/business_profiles/update")
    val collections by json["collections"].byModel<ApplicationRateLimit?>("/collections/")
    val collectionsAdd by json["collections"].byModel<ApplicationRateLimit?>("/collections/add")
    val collectionsCollection by json["collections"].byModel<ApplicationRateLimit?>("/collections/collection")
    val collectionsCreate by json["collections"].byModel<ApplicationRateLimit?>("/collections/create")
    val collectionsCurate by json["collections"].byModel<ApplicationRateLimit?>("/collections/curate")
    val collectionsDestroy by json["collections"].byModel<ApplicationRateLimit?>("/collections/destroy")
    val collectionsEntries by json["collections"].byModel<ApplicationRateLimit?>("/collections/entries")
    val collectionsFollow by json["collections"].byModel<ApplicationRateLimit?>("/collections/follow")
    val collectionsList by json["collections"].byModel<ApplicationRateLimit?>("/collections/list")
    val collectionsRemove by json["collections"].byModel<ApplicationRateLimit?>("/collections/remove")
    val collectionsShow by json["collections"].byModel<ApplicationRateLimit?>("/collections/show")
    val collectionsUnfollow by json["collections"].byModel<ApplicationRateLimit?>("/collections/unfollow")
    val collectionsUpdate by json["collections"].byModel<ApplicationRateLimit?>("/collections/update")
    val commerceAddresses by json["commerce"].byModel<ApplicationRateLimit?>("/commerce/addresses")
    val commercePaymentMethods by json["commerce"].byModel<ApplicationRateLimit?>("/commerce/payment_methods")
    val commerceProducts by json["commerce"].byModel<ApplicationRateLimit?>("/commerce/products")
    val commerceProfiles by json["commerce"].byModel<ApplicationRateLimit?>("/commerce/profiles")
    val commerceUserProfiles by json["commerce"].byModel<ApplicationRateLimit?>("/commerce/user_profiles")
    val communities1CommunitiesCreate by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/communities/create")
    val communities1CommunitiesShow by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/communities/show")
    val communities1CommunityIdJoin by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/community/:id/join")
    val communities1CommunityIdLeave by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/community/:id/leave")
    val communities1CommunityIdMembers by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/community/:id/members")
    val communities1CommunityIdShow by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/community/:id/show")
    val communities1CommunityIdTimeline by json["communities"].byModel<ApplicationRateLimit?>("/communities/1/community/:id/timeline")
    val contacts by json["contacts"].byModel<ApplicationRateLimit?>("/contacts")
    val contactsAddressbook by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/addressbook")
    val contactsConnectedUsers by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/connected_users")
    val contactsDeleteStatus by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/delete/status")
    val contactsUploadAndMatch by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/upload_and_match")
    val contactsUploadedBy by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/uploaded_by")
    val contactsUsers by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/users")
    val contactsUsersAndUploadedBy by json["contacts"].byModel<ApplicationRateLimit?>("/contacts/users_and_uploaded_by")
    val contentRecommendations by json["content"].byModel<ApplicationRateLimit?>("/content/recommendations")
    val conversationShowId by json["conversation"].byModel<ApplicationRateLimit?>("/conversation/show/:id")
    val customProfilesList by json["custom_profiles"].byModel<ApplicationRateLimit?>("/custom_profiles/list")
    val customProfilesShow by json["custom_profiles"].byModel<ApplicationRateLimit?>("/custom_profiles/show")
    val deviceInstallReferrer by json["device"].byModel<ApplicationRateLimit?>("/device/install_referrer")
    val deviceOperatorSignupInfo by json["device"].byModel<ApplicationRateLimit?>("/device/operator_signup_info")
    val deviceRegister by json["device"].byModel<ApplicationRateLimit?>("/device/register")
    val deviceSmsVerifyBegin by json["device"].byModel<ApplicationRateLimit?>("/device/sms_verify_begin")
    val deviceToken by json["device"].byModel<ApplicationRateLimit?>("/device/token")
    val deviceFollowingIds by json["device_following"].byModel<ApplicationRateLimit?>("/device_following/ids")
    val deviceFollowingList by json["device_following"].byModel<ApplicationRateLimit?>("/device_following/list")
    val directMessages by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages")
    val directMessagesBroadcastsList by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/broadcasts/list")
    val directMessagesBroadcastsShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/broadcasts/show")
    val directMessagesBroadcastsStatusesList by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/broadcasts/statuses/list")
    val directMessagesBroadcastsStatusesShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/broadcasts/statuses/show")
    val directMessagesEventsList by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/events/list")
    val directMessagesEventsShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/events/show")
    val directMessagesMarkRead by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/mark_read")
    val directMessagesSent by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/sent")
    val directMessagesSentAndReceived by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/sent_and_received")
    val directMessagesShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/show")
    val directMessagesSubscribersIds by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/ids")
    val directMessagesSubscribersListsList by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/lists/list")
    val directMessagesSubscribersListsMembersIds by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/lists/members/ids")
    val directMessagesSubscribersListsMembersShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/lists/members/show")
    val directMessagesSubscribersListsShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/lists/show")
    val directMessagesSubscribersShow by json["direct_messages"].byModel<ApplicationRateLimit?>("/direct_messages/subscribers/show")
    val discoverHighlight by json["discover"].byModel<ApplicationRateLimit?>("/discover/highlight")
    val discoverHome by json["discover"].byModel<ApplicationRateLimit?>("/discover/home")
    val discoverNearby by json["discover"].byModel<ApplicationRateLimit?>("/discover/nearby")
    val discoverUniversal by json["discover"].byModel<ApplicationRateLimit?>("/discover/universal")
    val dmConversationId by json["dm"].byModel<ApplicationRateLimit?>("/dm/conversation/:id")
    val dmConversationIdMetadata by json["dm"].byModel<ApplicationRateLimit?>("/dm/conversation/:id/metadata")
    val dmDestroy by json["dm"].byModel<ApplicationRateLimit?>("/dm/destroy")
    val dmInboxInitialState by json["dm"].byModel<ApplicationRateLimit?>("/dm/inbox_initial_state")
    val dmInboxTimelineId by json["dm"].byModel<ApplicationRateLimit?>("/dm/inbox_timeline/:Id")
    val dmPermissions by json["dm"].byModel<ApplicationRateLimit?>("/dm/permissions")
    val dmRequests by json["dm"].byModel<ApplicationRateLimit?>("/dm/requests")
    val dmUnreadCount by json["dm"].byModel<ApplicationRateLimit?>("/dm/unread_count")
    val dmUserInbox by json["dm"].byModel<ApplicationRateLimit?>("/dm/user_inbox")
    val dmUserUpdates by json["dm"].byModel<ApplicationRateLimit?>("/dm/user_updates")
    val draftsStatusesCreate by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/create")
    val draftsStatusesDestroy by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/destroy")
    val draftsStatusesIds by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/ids")
    val draftsStatusesList by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/list")
    val draftsStatusesShow by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/show")
    val draftsStatusesUpdate by json["drafts"].byModel<ApplicationRateLimit?>("/drafts/statuses/update")
    val exploreTiles by json["explore"].byModel<ApplicationRateLimit?>("/explore/tiles")
    val favoriteUsersIds by json["favorite_users"].byModel<ApplicationRateLimit?>("/favorite_users/ids")
    val favoriteUsersList by json["favorite_users"].byModel<ApplicationRateLimit?>("/favorite_users/list")
    val favoritesList by json["favorites"].byModel<ApplicationRateLimit?>("/favorites/list")
    val feedbackEvents by json["feedback"].byModel<ApplicationRateLimit?>("/feedback/events")
    val feedbackShowId by json["feedback"].byModel<ApplicationRateLimit?>("/feedback/show/:id")
    val followSearchShow by json["follow_search"].byModel<ApplicationRateLimit?>("/follow_search/show")
    val followersIds by json["followers"].byModel<ApplicationRateLimit?>("/followers/ids")
    val followersList by json["followers"].byModel<ApplicationRateLimit?>("/followers/list")
    val followersVitIds by json["followers"].byModel<ApplicationRateLimit?>("/followers/vit/ids")
    val followersVitList by json["followers"].byModel<ApplicationRateLimit?>("/followers/vit/list")
    val foundmediaCategories by json["foundmedia"].byModel<ApplicationRateLimit?>("/foundmedia/categories")
    val foundmediaCategoriesCategory by json["foundmedia"].byModel<ApplicationRateLimit?>("/foundmedia/categories/:category")
    val foundmediaSearch by json["foundmedia"].byModel<ApplicationRateLimit?>("/foundmedia/search")
    val friendsFollowingIds by json["friends"].byModel<ApplicationRateLimit?>("/friends/following/ids")
    val friendsFollowingList by json["friends"].byModel<ApplicationRateLimit?>("/friends/following/list")
    val friendsIds by json["friends"].byModel<ApplicationRateLimit?>("/friends/ids")
    val friendsList by json["friends"].byModel<ApplicationRateLimit?>("/friends/list")
    val friendsVitIds by json["friends"].byModel<ApplicationRateLimit?>("/friends/vit/ids")
    val friendsVitList by json["friends"].byModel<ApplicationRateLimit?>("/friends/vit/list")
    val friendshipsCreate by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/create")
    val friendshipsIncoming by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/incoming")
    val friendshipsList by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/list")
    val friendshipsLookup by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/lookup")
    val friendshipsNoRetweetsIds by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/no_retweets/ids")
    val friendshipsOutgoing by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/outgoing")
    val friendshipsShow by json["friendships"].byModel<ApplicationRateLimit?>("/friendships/show")
    val geoIdPlaceId by json["geo"].byModel<ApplicationRateLimit?>("/geo/id/:place_id")
    val geoPlacePage by json["geo"].byModel<ApplicationRateLimit?>("/geo/place_page")
    val geoPlaces by json["geo"].byModel<ApplicationRateLimit?>("/geo/places")
    val geoReverseGeocode by json["geo"].byModel<ApplicationRateLimit?>("/geo/reverse_geocode")
    val geoSearch by json["geo"].byModel<ApplicationRateLimit?>("/geo/search")
    val geoSimilarPlaces by json["geo"].byModel<ApplicationRateLimit?>("/geo/similar_places")
    val graphAppOptoutStatus by json["graph"].byModel<ApplicationRateLimit?>("/graph/app/optout/status")
    val graphql by json["graphql"].byModel<ApplicationRateLimit?>("/graphql")
    val guide by json["guide"].byModel<ApplicationRateLimit?>("/guide")
    val guideTopic by json["guide"].byModel<ApplicationRateLimit?>("/guide/topic")
    val helpConfiguration by json["help"].byModel<ApplicationRateLimit?>("/help/configuration")
    val helpExperiments by json["help"].byModel<ApplicationRateLimit?>("/help/experiments")
    val helpLanguages by json["help"].byModel<ApplicationRateLimit?>("/help/languages")
    val helpPrivacy by json["help"].byModel<ApplicationRateLimit?>("/help/privacy")
    val helpSettings by json["help"].byModel<ApplicationRateLimit?>("/help/settings")
    val helpTos by json["help"].byModel<ApplicationRateLimit?>("/help/tos")
    val iConfig by json["i"].byModel<ApplicationRateLimit?>("/i/config")
    val interestsSuggestions by json["interests"].byModel<ApplicationRateLimit?>("/interests/suggestions")
    val listsList by json["lists"].byModel<ApplicationRateLimit?>("/lists/list")
    val listsMembers by json["lists"].byModel<ApplicationRateLimit?>("/lists/members")
    val listsMembersShow by json["lists"].byModel<ApplicationRateLimit?>("/lists/members/show")
    val listsMemberships by json["lists"].byModel<ApplicationRateLimit?>("/lists/memberships")
    val listsOwnerships by json["lists"].byModel<ApplicationRateLimit?>("/lists/ownerships")
    val listsShow by json["lists"].byModel<ApplicationRateLimit?>("/lists/show")
    val listsStatuses by json["lists"].byModel<ApplicationRateLimit?>("/lists/statuses")
    val listsSubscribers by json["lists"].byModel<ApplicationRateLimit?>("/lists/subscribers")
    val listsSubscribersShow by json["lists"].byModel<ApplicationRateLimit?>("/lists/subscribers/show")
    val listsSubscriptions by json["lists"].byModel<ApplicationRateLimit?>("/lists/subscriptions")
    val liveEvent1IdTimeline by json["live_event"].byModel<ApplicationRateLimit?>("/live_event/1/:id/timeline")
    val livePipelineEvents by json["live_pipeline"].byModel<ApplicationRateLimit?>("/live_pipeline/events")
    val liveVideo1IdTimeline by json["live_video"].byModel<ApplicationRateLimit?>("/live_video/1/:id/timeline")
    val liveVideoStreamStatusId by json["live_video_stream"].byModel<ApplicationRateLimit?>("/live_video_stream/status/:id")
    val mediaUpload by json["media"].byModel<ApplicationRateLimit?>("/media/upload")
    val mobIdsyncGenerate by json["mob_idsync_generate"].byModel<ApplicationRateLimit?>("/mob_idsync_generate")
    val momentsAnnotateTimeline by json["moments"].byModel<ApplicationRateLimit?>("/moments/annotate_timeline")
    val momentsCapsuleId by json["moments"].byModel<ApplicationRateLimit?>("/moments/capsule/:id")
    val momentsCapsuleMomentid by json["moments"].byModel<ApplicationRateLimit?>("/moments/capsule/momentID")
    val momentsCategories by json["moments"].byModel<ApplicationRateLimit?>("/moments/categories")
    val momentsCategoriesTttCategories by json["moments"].byModel<ApplicationRateLimit?>("/moments/categories/ttt_categories")
    val momentsCreate by json["moments"].byModel<ApplicationRateLimit?>("/moments/create")
    val momentsCurateId by json["moments"].byModel<ApplicationRateLimit?>("/moments/curate/:id")
    val momentsCurateMetadataId by json["moments"].byModel<ApplicationRateLimit?>("/moments/curate_metadata/:id")
    val momentsDelete by json["moments"].byModel<ApplicationRateLimit?>("/moments/delete")
    val momentsFeedback by json["moments"].byModel<ApplicationRateLimit?>("/moments/feedback")
    val momentsGetRecommendedTweets by json["moments"].byModel<ApplicationRateLimit?>("/moments/get_recommended_tweets")
    val momentsGuide by json["moments"].byModel<ApplicationRateLimit?>("/moments/guide")
    val momentsLike by json["moments"].byModel<ApplicationRateLimit?>("/moments/like")
    val momentsList by json["moments"].byModel<ApplicationRateLimit?>("/moments/list")
    val momentsListCategories by json["moments"].byModel<ApplicationRateLimit?>("/moments/list_categories")
    val momentsListUserMoments by json["moments"].byModel<ApplicationRateLimit?>("/moments/list_user_moments")
    val momentsModernGuide by json["moments"].byModel<ApplicationRateLimit?>("/moments/modern_guide")
    val momentsPermissions by json["moments"].byModel<ApplicationRateLimit?>("/moments/permissions")
    val momentsPivot by json["moments"].byModel<ApplicationRateLimit?>("/moments/pivot")
    val momentsPublish by json["moments"].byModel<ApplicationRateLimit?>("/moments/publish")
    val momentsSearch by json["moments"].byModel<ApplicationRateLimit?>("/moments/search")
    val momentsShowId by json["moments"].byModel<ApplicationRateLimit?>("/moments/show/:id")
    val momentsSportsScores by json["moments"].byModel<ApplicationRateLimit?>("/moments/sports/scores")
    val momentsSubscribe by json["moments"].byModel<ApplicationRateLimit?>("/moments/subscribe")
    val momentsUnlike by json["moments"].byModel<ApplicationRateLimit?>("/moments/unlike")
    val momentsUnsubscribe by json["moments"].byModel<ApplicationRateLimit?>("/moments/unsubscribe")
    val momentsUpdateId by json["moments"].byModel<ApplicationRateLimit?>("/moments/update/:id")
    val momentsUpsert by json["moments"].byModel<ApplicationRateLimit?>("/moments/upsert")
    val momentsUserlikes by json["moments"].byModel<ApplicationRateLimit?>("/moments/userlikes")
    val momentsUsers by json["moments"].byModel<ApplicationRateLimit?>("/moments/users")
    val mutesAdvancedFilters by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/advanced_filters")
    val mutesKeywordsCreate by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/keywords/create")
    val mutesKeywordsDestroy by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/keywords/destroy")
    val mutesKeywordsDiscouraged by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/keywords/discouraged")
    val mutesKeywordsList by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/keywords/list")
    val mutesUsersIds by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/users/ids")
    val mutesUsersList by json["mutes"].byModel<ApplicationRateLimit?>("/mutes/users/list")
    val newsDetails by json["news"].byModel<ApplicationRateLimit?>("/news/details")
    val newsRankings by json["news"].byModel<ApplicationRateLimit?>("/news/rankings")
    val newsTop by json["news"].byModel<ApplicationRateLimit?>("/news/top")
    val notificationsId by json["notifications"].byModel<ApplicationRateLimit?>("/notifications/:id")
    val notificationsIdUnreadCount by json["notifications"].byModel<ApplicationRateLimit?>("/notifications/:id/unread_count")
    val notificationsViewId by json["notifications"].byModel<ApplicationRateLimit?>("/notifications/view/:id")
    val oauthAuthenticatePeriscope by json["oauth"].byModel<ApplicationRateLimit?>("/oauth/authenticate_periscope")
    val oauthList by json["oauth"].byModel<ApplicationRateLimit?>("/oauth/list")
    val offers by json["offers"].byModel<ApplicationRateLimit?>("/offers")
    val peopleDiscoveryModule by json["people_discovery"].byModel<ApplicationRateLimit?>("/people_discovery/module")
    val peopleDiscoveryModules by json["people_discovery"].byModel<ApplicationRateLimit?>("/people_discovery/modules")
    val profileSpotlightShow by json["profile_spotlight"].byModel<ApplicationRateLimit?>("/profile_spotlight/show")
    val promotedContentLog by json["promoted_content"].byModel<ApplicationRateLimit?>("/promoted_content/log")
    val promptsRecordEvent by json["prompts"].byModel<ApplicationRateLimit?>("/prompts/record_event")
    val promptsSuggest by json["prompts"].byModel<ApplicationRateLimit?>("/prompts/suggest")
    val pushDestinationsDevice by json["push_destinations"].byModel<ApplicationRateLimit?>("/push_destinations/device")
    val pushDestinationsList by json["push_destinations"].byModel<ApplicationRateLimit?>("/push_destinations/list")
    val relatedResultsShowId by json["related_results"].byModel<ApplicationRateLimit?>("/related_results/show/:id")
    val sandboxAccountActivityWebhooksIdSubscriptions by json["sandbox"].byModel<ApplicationRateLimit?>("/sandbox/account_activity/webhooks/:id/subscriptions")
    val savedSearchesDestroyId by json["saved_searches"].byModel<ApplicationRateLimit?>("/saved_searches/destroy/:id")
    val savedSearchesList by json["saved_searches"].byModel<ApplicationRateLimit?>("/saved_searches/list")
    val savedSearchesShowId by json["saved_searches"].byModel<ApplicationRateLimit?>("/saved_searches/show/:id")
    val scheduleStatusId by json["schedule"].byModel<ApplicationRateLimit?>("/schedule/status/:id")
    val scheduleStatusList by json["schedule"].byModel<ApplicationRateLimit?>("/schedule/status/list")
    val scheduleStatusLookup by json["schedule"].byModel<ApplicationRateLimit?>("/schedule/status/lookup")
    val searchAdaptive by json["search"].byModel<ApplicationRateLimit?>("/search/adaptive")
    val searchTweets by json["search"].byModel<ApplicationRateLimit?>("/search/tweets")
    val searchTypeahead by json["search"].byModel<ApplicationRateLimit?>("/search/typeahead")
    val searchUniversal by json["search"].byModel<ApplicationRateLimit?>("/search/universal")
    val searchrecordingsList by json["searchrecordings"].byModel<ApplicationRateLimit?>("/searchrecordings/list")
    val searchrecordingsShow by json["searchrecordings"].byModel<ApplicationRateLimit?>("/searchrecordings/show")
    val specialEventsWorldCup2014CountriesList by json["special_events"].byModel<ApplicationRateLimit?>("/special_events/world_cup_2014/countries/list")
    val specialEventsWorldCup2014Settings by json["special_events"].byModel<ApplicationRateLimit?>("/special_events/world_cup_2014/settings")
    val stations by json["stations"].byModel<ApplicationRateLimit?>("/stations/*")
    val statusesIdActivitySummary by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/:id/activity/summary")
    val statusesFavoritedBy by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/favorited_by")
    val statusesFollowingTimeline by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/following_timeline")
    val statusesFriends by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/friends")
    val statusesHomeTimeline by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/home_timeline")
    val statusesLookup by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/lookup")
    val statusesMediaTimeline by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/media_timeline")
    val statusesMentionsTimeline by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/mentions_timeline")
    val statusesOembed by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/oembed")
    val statusesRetweetedBy by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/retweeted_by")
    val statusesRetweetersIds by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/retweeters/ids")
    val statusesRetweetsId by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/retweets/:id")
    val statusesRetweetsOfMe by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/retweets_of_me")
    val statusesShowId by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/show/:id")
    val statusesUserTimeline by json["statuses"].byModel<ApplicationRateLimit?>("/statuses/user_timeline")
    val stickerproviderCatalog by json["stickerprovider"].byModel<ApplicationRateLimit?>("/stickerprovider/catalog")
    val stickerproviderStickersId by json["stickerprovider"].byModel<ApplicationRateLimit?>("/stickerprovider/stickers/:id")
    val storystreamStories by json["storystream"].byModel<ApplicationRateLimit?>("/storystream/stories")
    val stratoColumnUserIdSearchSearchsafetyreadonly by json["strato"].byModel<ApplicationRateLimit?>("/strato/column/User/:id/search/searchSafetyReadonly")
    val streamsCategories by json["streams"].byModel<ApplicationRateLimit?>("/streams/categories")
    val streamsRecommendedModules by json["streams"].byModel<ApplicationRateLimit?>("/streams/recommended_modules")
    val streamsRecommendedVideos by json["streams"].byModel<ApplicationRateLimit?>("/streams/recommended_videos")
    val streamsRelatedUsers by json["streams"].byModel<ApplicationRateLimit?>("/streams/related_users")
    val streamsStream by json["streams"].byModel<ApplicationRateLimit?>("/streams/stream")
    val tfbV1QuickPromoteStatusesMostRecentlyActive by json["tfb"].byModel<ApplicationRateLimit?>("/tfb/v1/quick_promote/statuses/most_recently_active")
    val tfbV1QuickPromoteStatusesTimeline by json["tfb"].byModel<ApplicationRateLimit?>("/tfb/v1/quick_promote/statuses/timeline")
    val timelineTeamCurationList by json["timeline-team"].byModel<ApplicationRateLimit?>("/timeline-team/curation/list")
    val timelineTeamTimelineShow by json["timeline-team"].byModel<ApplicationRateLimit?>("/timeline-team/timeline/show")
    val timelineTeamTimelinesCustomList by json["timeline-team"].byModel<ApplicationRateLimit?>("/timeline-team/timelines/custom/list")
    val timelineTeamTimelinesCustomShow by json["timeline-team"].byModel<ApplicationRateLimit?>("/timeline-team/timelines/custom/show")
    val timelineTeamTimelinesTimeline by json["timeline-team"].byModel<ApplicationRateLimit?>("/timeline-team/timelines/timeline")
    val timelineConversationId by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/conversation/:id")
    val timelineFavoritesId by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/favorites/:id")
    val timelineHome by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/home")
    val timelineIcymi by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/icymi")
    val timelineLikedBy by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/liked_by")
    val timelineList by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/list")
    val timelineMediaId by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/media/:id")
    val timelineProfileId by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/profile/:id")
    val timelineReactive by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/reactive")
    val timelineRetweetedBy by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/retweeted_by")
    val timelineUserId by json["timeline"].byModel<ApplicationRateLimit?>("/timeline/user/:id")
    val timelinesFollow by json["timelines"].byModel<ApplicationRateLimit?>("/timelines/follow")
    val timelinesUnfollow by json["timelines"].byModel<ApplicationRateLimit?>("/timelines/unfollow")
    val translationsShow by json["translations"].byModel<ApplicationRateLimit?>("/translations/show")
    val trendsAvailable by json["trends"].byModel<ApplicationRateLimit?>("/trends/available")
    val trendsClosest by json["trends"].byModel<ApplicationRateLimit?>("/trends/closest")
    val trendsPersonalized by json["trends"].byModel<ApplicationRateLimit?>("/trends/personalized")
    val trendsPlace by json["trends"].byModel<ApplicationRateLimit?>("/trends/place")
    val trendsPlus by json["trends"].byModel<ApplicationRateLimit?>("/trends/plus")
    val trendsTimeline by json["trends"].byModel<ApplicationRateLimit?>("/trends/timeline")
    val tvShowsId by json["tv"].byModel<ApplicationRateLimit?>("/tv/shows/:id")
    val tvTelecastsId by json["tv"].byModel<ApplicationRateLimit?>("/tv/telecasts/:id")
    val tweetPromptsReportInteraction by json["tweet_prompts"].byModel<ApplicationRateLimit?>("/tweet_prompts/report_interaction")
    val tweetPromptsShow by json["tweet_prompts"].byModel<ApplicationRateLimit?>("/tweet_prompts/show")
    val tweetdeckClients by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/clients")
    val tweetdeckClientsBlackbird by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/clients/blackbird")
    val tweetdeckClientsBlackbirdAll by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/clients/blackbird/all")
    val tweetdeckClientsPro by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/clients/pro")
    val tweetdeckClientsProAll by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/clients/pro/all")
    val tweetdeckColumns by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/columns")
    val tweetdeckDataminrAuthtoken by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/dataminr/authtoken")
    val tweetdeckFeeds by json["tweetdeck"].byModel<ApplicationRateLimit?>("/tweetdeck/feeds")
    val urlsClick by json["urls"].byModel<ApplicationRateLimit?>("/urls/click")
    val urlsExpand by json["urls"].byModel<ApplicationRateLimit?>("/urls/expand")
    val usersContributees by json["users"].byModel<ApplicationRateLimit?>("/users/contributees")
    val usersContributeesPending by json["users"].byModel<ApplicationRateLimit?>("/users/contributees/pending")
    val usersContributors by json["users"].byModel<ApplicationRateLimit?>("/users/contributors")
    val usersContributorsPending by json["users"].byModel<ApplicationRateLimit?>("/users/contributors/pending")
    val usersDerivedInfo by json["users"].byModel<ApplicationRateLimit?>("/users/derived_info")
    val usersEmailPhoneInfo by json["users"].byModel<ApplicationRateLimit?>("/users/email_phone_info")
    val usersExtendedProfile by json["users"].byModel<ApplicationRateLimit?>("/users/extended_profile")
    val usersFollowingFollowersOf by json["users"].byModel<ApplicationRateLimit?>("/users/following_followers_of")
    val usersInterestsTimelines by json["users"].byModel<ApplicationRateLimit?>("/users/interests/timelines")
    val usersInterestsTopics by json["users"].byModel<ApplicationRateLimit?>("/users/interests/topics")
    val usersLookup by json["users"].byModel<ApplicationRateLimit?>("/users/lookup")
    val usersPhoneNumberAvailable by json["users"].byModel<ApplicationRateLimit?>("/users/phone_number_available")
    val usersProfileBanner by json["users"].byModel<ApplicationRateLimit?>("/users/profile_banner")
    val usersRecommendations by json["users"].byModel<ApplicationRateLimit?>("/users/recommendations")
    val usersReportSpam by json["users"].byModel<ApplicationRateLimit?>("/users/report_spam")
    val usersReverseLookup by json["users"].byModel<ApplicationRateLimit?>("/users/reverse_lookup")
    val usersSearch by json["users"].byModel<ApplicationRateLimit?>("/users/search")
    val usersSendInvitesByEmail by json["users"].byModel<ApplicationRateLimit?>("/users/send_invites_by_email")
    val usersShowId by json["users"].byModel<ApplicationRateLimit?>("/users/show/:id")
    val usersSuggestions by json["users"].byModel<ApplicationRateLimit?>("/users/suggestions")
    val usersSuggestionsSlug by json["users"].byModel<ApplicationRateLimit?>("/users/suggestions/:slug")
    val usersSuggestionsSlugMembers by json["users"].byModel<ApplicationRateLimit?>("/users/suggestions/:slug/members")
    val usersWipeAddressbook by json["users"].byModel<ApplicationRateLimit?>("/users/wipe_addressbook")
    val usersDerivedinfo by json["users_derived.info"].byModel<ApplicationRateLimit?>("/users_derived.info")
    val videosTypeConfigId by json["videos"].byModel<ApplicationRateLimit?>("/videos/:type/config/:id")
    val videosSuggestions by json["videos"].byModel<ApplicationRateLimit?>("/videos/suggestions")
    val webhooks by json["webhooks"].byModel<ApplicationRateLimit?>("/webhooks")
    val webhooksSubscriptionsDirectMessages by json["webhooks"].byModel<ApplicationRateLimit?>("/webhooks/subscriptions/direct_messages")
}