package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class ListsListModel(val json: JsonElement) {
    val createdAt by json.byNullableString("created_at") // "Thu Mar 09 15:25:06 +0000 2017"
    val description by json.byNullableString // ""
    val following by json.byNullableBool // true
    val fullName by json.byNullableString("full_name") // "@SlashNephy/dtv"
    val id by json.byNullableLong // 839859596961411072
    val idStr by json.byNullableString("id_str") // "839859596961411072"
    val memberCount by json.byNullableInt("member_count") // 2
    val mode by json.byNullableString // "private"
    val name by json.byNullableString // "DTV"
    val slug by json.byNullableString // "dtv"
    val subscriberCount by json.byNullableInt("subscriber_count") // 0
    val uri by json.byNullableString // "/SlashNephy/lists/dtv"
    val user by json.byNullableObject // {"id": 701282649466245120, "id_str": "701282649466245120", "name": "安達としまむら", "screen_name": "SlashNephy", "location": "Sendai, Japan", "description": "Python / Kotlin / #ワンライナーは正義\nhttps://t.co/8udUawi147 @GeForce_GTX1080", "url": "https://t.co/UDBxOXmvoG", "entities": {"url": {"urls": [{"url": "https://t.co/UDBxOXmvoG", "expanded_url": "https://slash.nephy.jp", "display_url": "slash.nephy.jp", "indices": [0, 23]}]}, "description": {"urls": [{"url": "https://t.co/8udUawi147", "expanded_url": "https://steamcommunity.com/id/slashnephy", "display_url": "steamcommunity.com/id/slashnephy", "indices": [29, 52]}]}}, "protected": false, "followers_count": 215, "fast_followers_count": 0, "normal_followers_count": 215, "friends_count": 311, "listed_count": 13, "created_at": "Sun Feb 21 05:49:47 +0000 2016", "favourites_count": 754, "utc_offset": 32400, "time_zone": "Tokyo", "geo_enabled": true, "verified": false, "statuses_count": 5175, "media_count": 436, "lang": "ja", "contributors_enabled": false, "is_translator": false, "is_translation_enabled": false, "profile_background_color": "000000", "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_tile": false, "profile_image_url": "http://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_image_url_https": "https://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_banner_url": "https://pbs.twimg.com/profile_banners/701282649466245120/1497800398", "profile_link_color": "1B95E0", "profile_sidebar_border_color": "000000", "profile_sidebar_fill_color": "000000", "profile_text_color": "000000", "profile_use_background_image": false, "has_extended_profile": true, "default_profile": false, "default_profile_image": false, "pinned_tweet_ids": [905874500566212608], "pinned_tweet_ids_str": ["905874500566212608"], "has_custom_timelines": true, "can_media_tag": true, "followed_by": false, "following": false, "follow_request_sent": false, "notifications": false, "business_profile_state": "none", "translator_type": "none"}
}
//list
class ListsList(oauth: OAuthRequestHandler) : AbsOAuthGet<ListsListModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/lists/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
