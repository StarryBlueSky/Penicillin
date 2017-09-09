package jp.nephy.penicillin.api

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

abstract class AbsOAuthGet<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.GET
}
