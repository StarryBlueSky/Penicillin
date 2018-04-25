package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.OAuth2Token
import jp.nephy.penicillin.request.AuthorizationType


class OAuth2(override val client: PenicillinClient): Endpoint {
    fun bearerToken(grantType: String = "client_credentials", vararg options: Pair<String, Any?>)= client.session.postObject<OAuth2Token>("https://api.twitter.com/oauth2/token", AuthorizationType.OAuth2RequestToken) {
        form("grant_type" to grantType, *options)
    }

    fun invalidateToken(bearerToken: String, vararg options: Pair<String, Any?>)= client.session.postObject<OAuth2Token>("https://api.twitter.com/oauth2/invalidate_token", AuthorizationType.OAuth2RequestToken) {
        form("access_token" to bearerToken, *options)
    }
}
