package jp.nephy.penicillin.api.endpoint

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement
import jp.nephy.penicillin.api.AbsOAuthGet
import jp.nephy.penicillin.api.Parameter
import jp.nephy.penicillin.api.ResponseFormats
import jp.nephy.penicillin.api.model.UserModel
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

class ListsMembersShow(oauth: OAuthRequestHandler) : AbsOAuthGet<UserModel>(oauth) {
    override val resourceUrl = "https://api.twitter.com/1.1/lists/members/show.json"
    override val responseFormat = ResponseFormats.JSON
    override val isRateLimited = true
    override val defaultParameter = Parameter()
}
