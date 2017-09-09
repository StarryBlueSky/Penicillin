package jp.nephy.penicillin.api

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

abstract class AbsOAuthPost<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.POST
}
