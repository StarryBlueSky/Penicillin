package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class DirectMessagesEventsListModel(val json: JsonElement) {
}

class DirectMessagesEventsList(oauth: OAuthRequestHandler) : AbsOAuthGet<DirectMessagesEventsListModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/direct_messages/events/list.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
