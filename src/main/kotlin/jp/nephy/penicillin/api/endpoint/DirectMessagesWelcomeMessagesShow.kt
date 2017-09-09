package jp.nephy.penicillin.api.endpoint

import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class DirectMessagesWelcomeMessagesShowModel(val json: JsonElement) {
}

class DirectMessagesWelcomeMessagesShow(oauth: OAuthRequestHandler) : AbsOAuthGet<DirectMessagesWelcomeMessagesShowModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/direct_messages/welcome_messages/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}