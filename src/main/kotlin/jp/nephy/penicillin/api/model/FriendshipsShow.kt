package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byNullableObject
import com.google.gson.JsonElement

class FriendshipsShow(val json: JsonElement) {
    val relationship by json.byNullableObject // {"source": {"id": 701282649466245120, "id_str": "701282649466245120", "screen_name": "SlashNephy", "following": false, "followed_by": false, "live_following": false, "following_received": false, "following_requested": false, "notifications_enabled": false, "can_dm": false, "can_media_tag": true, "blocking": false, "blocked_by": false, "muting": false, "want_retweets": false, "all_replies": false, "marked_spam": false}, "target": {"id": 7080152, "id_str": "7080152", "screen_name": "TwitterJP", "following": false, "followed_by": false, "following_received": false, "following_requested": false}}
}
