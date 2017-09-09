package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FriendshipsShowModel(val json: JsonElement) {
    val relationship by json.byNullableObject // {"source": {"id": 701282649466245120, "id_str": "701282649466245120", "screen_name": "SlashNephy", "following": false, "followed_by": false, "live_following": false, "following_received": false, "following_requested": false, "notifications_enabled": false, "can_dm": false, "can_media_tag": true, "blocking": false, "blocked_by": false, "muting": false, "want_retweets": false, "all_replies": false, "marked_spam": false}, "target": {"id": 7080152, "id_str": "7080152", "screen_name": "TwitterJP", "following": false, "followed_by": false, "following_received": false, "following_requested": false}}
}

class FriendshipsShow(oauth: OAuthRequestHandler) : AbsOAuthGet<FriendshipsShowModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/friendships/show.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
