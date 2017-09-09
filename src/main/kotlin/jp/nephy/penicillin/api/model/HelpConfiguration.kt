package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableArray
import com.github.salomonbrys.kotson.byNullableInt
import com.github.salomonbrys.kotson.byNullableObject
import com.github.salomonbrys.kotson.byNullableString
import com.google.gson.JsonElement

class HelpConfiguration(val json: JsonElement) {
    val charactersReservedPerMedia by json.byNullableInt("characters_reserved_per_media") // 24
    val clientEventUrl by json.byNullableString("client_event_url") // "https://twitter.com/scribe"
    val dmTextCharacterLimit by json.byNullableInt("dm_text_character_limit") // 10000
    val maxMediaPerUpload by json.byNullableInt("max_media_per_upload") // 1
    val nonUsernamePaths by json.byNullableArray("non_username_paths") // ["about", "account", "accounts", "activity", "all", "announcements", "anywhere", "api_rules", "api_terms", "apirules", "apps", "auth", "badges", "blog", "business", "buttons", "contacts", "devices", "direct_messages", "download", "downloads", "edit_announcements", "faq", "favorites", "find_sources", "find_users", "followers", "following", "friend_request", "friendrequest", "friends", "goodies", "help", "home", "i", "im_account", "inbox", "invitations", "invite", "jobs", "list", "login", "logo", "logout", "me", "mentions", "messages", "mockview", "newtwitter", "notifications", "nudge", "oauth", "phoenix_search", "positions", "privacy", "public_timeline", "related_tweets", "replies", "retweeted_of_mine", "retweets", "retweets_by_others", "rules", "saved_searches", "search", "sent", "sessions", "settings", "share", "signup", "signin", "similar_to", "statistics", "terms", "tos", "translate", "trends", "tweetbutton", "twttr", "update_discoverability", "users", "welcome", "who_to_follow", "widgets", "zendesk_auth", "media_signup"]
    val photoSizeLimit by json.byNullableInt("photo_size_limit") // 3145728
    val photoSizes by json.byNullableObject("photo_sizes") // {"thumb": {"h": 150, "resize": "crop", "w": 150}, "small": {"h": 480, "resize": "fit", "w": 340}, "medium": {"h": 1200, "resize": "fit", "w": 600}, "large": {"h": 2048, "resize": "fit", "w": 1024}}
    val shortUrlLength by json.byNullableInt("short_url_length") // 23
    val shortUrlLengthHttps by json.byNullableInt("short_url_length_https") // 23
}
