package jp.nephy.penicillin.api

import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler

abstract class AbsOAuthDelete<T>(oauth: OAuthRequestHandler): AbsOAuthEndPoint<T>(oauth) {
    override val method = HTTPMethod.DELETE
}
