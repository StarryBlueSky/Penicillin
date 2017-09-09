package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class DirectMessagesSentModel(val json: JsonElement) {
    val createdAt by json.byNullableString("created_at") // "Sat Aug 12 11:35:52 +0000 2017"
    val entities by json.byNullableObject // {"hashtags": [], "symbols": [], "user_mentions": [], "urls": []}
    val id by json.byNullableLong // 896334414019411972
    val idStr by json.byNullableString("id_str") // "896334414019411972"
    val recipient by json.byNullableObject // {"id": 1125217380, "id_str": "1125217380", "name": "動議動議～！ｗ", "screen_name": "kobatatu915", "location": "", "description": "千菅春香さん//経済学部/国際交流/起業/勝ち抜く就活/ぽんしゅ/家電/すぴぐら/4×5㌅/PENTAX/高垣楓/佐藤心/俊龍/田淵智也/渡辺量/遠山明孝/JR75.35%", "url": "https://t.co/dTlqoFy0Vm", "entities": {"url": {"urls": [{"url": "https://t.co/dTlqoFy0Vm", "expanded_url": "https://www.flickr.com/photos/145861347@N06/", "display_url": "flickr.com/photos/1458613…", "indices": [0, 23]}]}, "description": {"urls": []}}, "protected": false, "followers_count": 1501, "friends_count": 1145, "listed_count": 78, "created_at": "Sun Jan 27 14:36:00 +0000 2013", "favourites_count": 139569, "utc_offset": 28800, "time_zone": "Irkutsk", "geo_enabled": true, "verified": false, "statuses_count": 208027, "lang": "ja", "contributors_enabled": false, "is_translator": false, "is_translation_enabled": false, "profile_background_color": "000000", "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_tile": false, "profile_image_url": "http://pbs.twimg.com/profile_images/870580121597378560/keqe2lBo_normal.jpg", "profile_image_url_https": "https://pbs.twimg.com/profile_images/870580121597378560/keqe2lBo_normal.jpg", "profile_banner_url": "https://pbs.twimg.com/profile_banners/1125217380/1476787973", "profile_link_color": "CCAA66", "profile_sidebar_border_color": "000000", "profile_sidebar_fill_color": "000000", "profile_text_color": "000000", "profile_use_background_image": false, "has_extended_profile": true, "default_profile": false, "default_profile_image": false, "following": true, "follow_request_sent": false, "notifications": true, "translator_type": "none"}
    val recipientId by json.byNullableInt("recipient_id") // 1125217380
    val recipientIdStr by json.byNullableString("recipient_id_str") // "1125217380"
    val recipientScreenName by json.byNullableString("recipient_screen_name") // "kobatatu915"
    val sender by json.byNullableObject // {"id": 701282649466245120, "id_str": "701282649466245120", "name": "安達としまむら", "screen_name": "SlashNephy", "location": "Sendai, Japan", "description": "Python / Kotlin / #ワンライナーは正義\nhttps://t.co/8udUawi147 @GeForce_GTX1080", "url": "https://t.co/UDBxOXmvoG", "entities": {"url": {"urls": [{"url": "https://t.co/UDBxOXmvoG", "expanded_url": "https://slash.nephy.jp", "display_url": "slash.nephy.jp", "indices": [0, 23]}]}, "description": {"urls": [{"url": "https://t.co/8udUawi147", "expanded_url": "https://steamcommunity.com/id/slashnephy", "display_url": "steamcommunity.com/id/slashnephy", "indices": [29, 52]}]}}, "protected": false, "followers_count": 215, "friends_count": 311, "listed_count": 13, "created_at": "Sun Feb 21 05:49:47 +0000 2016", "favourites_count": 753, "utc_offset": 32400, "time_zone": "Tokyo", "geo_enabled": true, "verified": false, "statuses_count": 5171, "lang": "ja", "contributors_enabled": false, "is_translator": false, "is_translation_enabled": false, "profile_background_color": "000000", "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_tile": false, "profile_image_url": "http://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_image_url_https": "https://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_banner_url": "https://pbs.twimg.com/profile_banners/701282649466245120/1497800398", "profile_link_color": "1B95E0", "profile_sidebar_border_color": "000000", "profile_sidebar_fill_color": "000000", "profile_text_color": "000000", "profile_use_background_image": false, "has_extended_profile": true, "default_profile": false, "default_profile_image": false, "following": false, "follow_request_sent": false, "notifications": false, "translator_type": "none"}
    val senderId by json.byNullableLong("sender_id") // 701282649466245120
    val senderIdStr by json.byNullableString("sender_id_str") // "701282649466245120"
    val senderScreenName by json.byNullableString("sender_screen_name") // "SlashNephy"
    val text by json.byNullableString // "***"
}
//list
class DirectMessagesSent(oauth: OAuthRequestHandler) : AbsOAuthGet<DirectMessagesSentModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/direct_messages/sent.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
