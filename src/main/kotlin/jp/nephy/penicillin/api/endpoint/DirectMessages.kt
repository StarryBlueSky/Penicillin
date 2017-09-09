package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class DirectMessagesModel(val json: JsonElement) {
    val createdAt by json.byNullableString("created_at") // "Wed Aug 23 06:02:35 +0000 2017"
    val entities by json.byNullableObject // {"hashtags": [{"text": "でか焼鳥無料プレゼント", "indices": [8, 20]}], "symbols": [], "user_mentions": [], "urls": []}
    val id by json.byNullableLong // 900236809275228164
    val idStr by json.byNullableString("id_str") // "900236809275228164"
    val read by json.byNullableBool // true
    val recipient by json.byNullableObject // {"id": 701282649466245120, "id_str": "701282649466245120", "name": "安達としまむら", "screen_name": "SlashNephy", "location": "Sendai, Japan", "description": "Python / Kotlin / #ワンライナーは正義\nhttps://t.co/8udUawi147 @GeForce_GTX1080", "url": "https://t.co/UDBxOXmvoG", "entities": {"url": {"urls": [{"url": "https://t.co/UDBxOXmvoG", "expanded_url": "https://slash.nephy.jp", "display_url": "slash.nephy.jp", "indices": [0, 23]}]}, "description": {"urls": [{"url": "https://t.co/8udUawi147", "expanded_url": "https://steamcommunity.com/id/slashnephy", "display_url": "steamcommunity.com/id/slashnephy", "indices": [29, 52]}]}}, "protected": false, "followers_count": 215, "friends_count": 311, "listed_count": 13, "created_at": "Sun Feb 21 05:49:47 +0000 2016", "favourites_count": 753, "utc_offset": 32400, "time_zone": "Tokyo", "geo_enabled": true, "verified": false, "statuses_count": 5171, "lang": "ja", "contributors_enabled": false, "is_translator": false, "is_translation_enabled": false, "profile_background_color": "000000", "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png", "profile_background_tile": false, "profile_image_url": "http://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_image_url_https": "https://pbs.twimg.com/profile_images/899302910164557824/j7LmkzoE_normal.jpg", "profile_banner_url": "https://pbs.twimg.com/profile_banners/701282649466245120/1497800398", "profile_link_color": "1B95E0", "profile_sidebar_border_color": "000000", "profile_sidebar_fill_color": "000000", "profile_text_color": "000000", "profile_use_background_image": false, "has_extended_profile": true, "default_profile": false, "default_profile_image": false, "following": false, "follow_request_sent": false, "notifications": false, "translator_type": "none"}
    val recipientId by json.byNullableLong("recipient_id") // 701282649466245120
    val recipientIdStr by json.byNullableString("recipient_id_str") // "701282649466245120"
    val recipientScreenName by json.byNullableString("recipient_screen_name") // "SlashNephy"
    val sender by json.byNullableObject // {"id": 115639376, "id_str": "115639376", "name": "ローソン", "screen_name": "akiko_lawson", "location": "", "description": "【ローソン公式Twitter】ローソンクルー♪あきこです(^^)最新情報をマイペースでお知らせします。instagramは https://t.co/ug6xZ4vRHx ☆ youtubeはhttps://t.co/yt55FzhTOk", "url": "https://t.co/8vXbTU361Y", "entities": {"url": {"urls": [{"url": "https://t.co/8vXbTU361Y", "expanded_url": "http://lawson.co.jp", "display_url": "lawson.co.jp", "indices": [0, 23]}]}, "description": {"urls": [{"url": "https://t.co/ug6xZ4vRHx", "expanded_url": "https://www.instagram.com/akiko_lawson/", "display_url": "instagram.com/akiko_lawson/", "indices": [62, 85]}, {"url": "https://t.co/yt55FzhTOk", "expanded_url": "https://m.youtube.com/user/lawsonnews", "display_url": "m.youtube.com/user/lawsonnews", "indices": [96, 119]}]}}, "protected": false, "followers_count": 1753487, "friends_count": 200274, "listed_count": 9966, "created_at": "Fri Feb 19 11:16:00 +0000 2010", "favourites_count": 123, "utc_offset": 32400, "time_zone": "Tokyo", "geo_enabled": false, "verified": true, "statuses_count": 4200621, "lang": "ja", "contributors_enabled": false, "is_translator": false, "is_translation_enabled": false, "profile_background_color": "C0DEED", "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/625674304131215360/uq9M_8JJ.jpg", "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/625674304131215360/uq9M_8JJ.jpg", "profile_background_tile": false, "profile_image_url": "http://pbs.twimg.com/profile_images/875517108884455426/q3HMm7hU_normal.jpg", "profile_image_url_https": "https://pbs.twimg.com/profile_images/875517108884455426/q3HMm7hU_normal.jpg", "profile_banner_url": "https://pbs.twimg.com/profile_banners/115639376/1504591689", "profile_link_color": "F5ABB5", "profile_sidebar_border_color": "FFFFFF", "profile_sidebar_fill_color": "DDEEF6", "profile_text_color": "333333", "profile_use_background_image": true, "has_extended_profile": false, "default_profile": false, "default_profile_image": false, "following": true, "follow_request_sent": false, "notifications": false, "translator_type": "none"}
    val senderId by json.byNullableInt("sender_id") // 115639376
    val senderIdStr by json.byNullableString("sender_id_str") // "115639376"
    val senderScreenName by json.byNullableString("sender_screen_name") // "akiko_lawson"
    val text by json.byNullableString // "11日間連続！ #でか焼鳥無料プレゼント キャンペーンにご当選されました！おめでとうございます♪\n\n全国のローソンで使用できるでか焼鳥無料券をお送りいたします。\n下記URLからクーポン画面へアクセスください。\n\n≪引換期間≫※必ずご確認ください。\n8月16日(水)00:00..."
}
//list
class DirectMessages(oauth: OAuthRequestHandler) : AbsOAuthGet<DirectMessagesModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/direct_messages.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
