@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.models.OAuth2Token


class OAuth2(override val client: PenicillinClient): Endpoint {
    fun bearerToken(grantType: String = "client_credentials", vararg options: Pair<String, Any?>) = client.session.post("/oauth2/token") {
        authType(AuthorizationType.OAuth2RequestToken)
        body {
            form {
                add("grant_type" to grantType, *options)
            }
        }
    }.jsonObject<OAuth2Token>()

    fun invalidateToken(bearerToken: String, vararg options: Pair<String, Any?>) = client.session.post("/oauth2/invalidate_token") {
        authType(AuthorizationType.OAuth2RequestToken)
        body {
            form {
                add("access_token" to bearerToken, *options)
            }
        }
    }.jsonObject<OAuth2Token>()
}
