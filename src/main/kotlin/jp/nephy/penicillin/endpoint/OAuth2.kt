package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.Recipe
import jp.nephy.penicillin.auth.AuthorizationType
import jp.nephy.penicillin.credential.BearerToken
import jp.nephy.penicillin.model.OAuth2Token
import jp.nephy.penicillin.response.ResponseObject

@Suppress("UNUSED")
class OAuth2(private val client: Client) {
    @POST
    fun getBearerTokenResponse(grantType: String="client_credentials", vararg options: Pair<String, String?>): ResponseObject<OAuth2Token> {
        return client.session.new()
                .type(AuthorizationType.OAuth2RequestToken)
                .url("https://api.twitter.com/oauth2/token")
                .dataAsForm("grant_type" to grantType)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun invalidateToken(token: BearerToken, vararg options: Pair<String, String?>): ResponseObject<OAuth2Token> {
        return client.session.new()
                .type(AuthorizationType.OAuth2RequestToken)
                .url("https://api.twitter.com/oauth2/invalidate_token")
                .dataAsForm("access_token" to token)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST @Recipe
    fun getBearerToken(): BearerToken {
        val response = getBearerTokenResponse()

        return BearerToken(response.result.token)
    }
}
