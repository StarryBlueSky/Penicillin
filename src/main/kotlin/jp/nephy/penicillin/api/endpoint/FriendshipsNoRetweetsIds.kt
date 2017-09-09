package jp.nephy.penicillin.api.endpoint

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FriendshipsNoRetweetsIdsModel(val json: JsonElement) {
    val ids = json.asJsonArray // []
}

class FriendshipsNoRetweetsIds(oauth: OAuthRequestHandler) : AbsOAuthGet<FriendshipsNoRetweetsIdsModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/friendships/no_retweets/ids.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
