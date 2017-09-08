package jp.nephy.penicillin.api.endpoint

import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.api.model.TweetModel
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class FavoritesList(oauth: OAuthRequestHandler): AbsOAuthGet<TweetModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/favorites/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val requestsPer15mins = 75
    override val defaultParameter = Parameter().apply {
        put("user_id", null)
        put("screen_name", null)
        put("count", "100")
        put("since_id", null)
        put("max_id", null)
        put("include_entities", "true")
    }
}