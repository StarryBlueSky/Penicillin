package jp.nephy.penicillin.endpoints

import io.ktor.http.ParametersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.auth.AuthorizationType
import jp.nephy.penicillin.models.special.AccessTokenResponse
import jp.nephy.penicillin.models.special.RequestTokenResponse


class OAuth(override val client: PenicillinClient): Endpoint {
    fun requestToken(callbackUrl: String = "oob", vararg options: Pair<String, Any?>): RequestTokenResponse {
        val result = client.session.post("/oauth/request_token") {
            authType(AuthorizationType.OAuth1a, callbackUrl)
            body {
                form {
                    add(*options)
                }
            }
        }.text().complete()

        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&oauth_callback_confirmed=(.+)$".toRegex()
        val (requestToken, requestTokenSecret, callbackConfirmed) = pattern.matchEntire(result.content)!!.destructured

        return RequestTokenResponse(requestToken, requestTokenSecret, callbackConfirmed.toBoolean())
    }

    fun authorizeUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        val parameters = ParametersBuilder()
        parameters["oauth_token"] = accessToken
        if (forceLogin != null) {
            parameters["force_login"] = forceLogin.toString()
        }
        if (screenName != null) {
            parameters["screen_name"] = screenName
        }

        return URLBuilder(protocol = URLProtocol.HTTPS, host = "api.twitter.com", encodedPath = "/oauth/authorize", parameters = parameters).buildString()
    }

    fun authenticateUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        val parameters = ParametersBuilder()
        parameters["oauth_token"] = accessToken
        if (forceLogin != null) {
            parameters["force_login"] = forceLogin.toString()
        }
        if (screenName != null) {
            parameters["screen_name"] = screenName
        }

        return URLBuilder(protocol = URLProtocol.HTTPS, host = "api.twitter.com", encodedPath = "/oauth/authenticate", parameters = parameters).buildString()
    }

    fun accessToken(requestToken: String, requestTokenSecret: String, verifier: String, vararg options: Pair<String, Any?>): AccessTokenResponse {
        val result = PenicillinClient {
            account {
                application(client.session.credentials.consumerKey!!, client.session.credentials.consumerSecret!!)
                token(requestToken, requestTokenSecret)
            }
        }.session.post("/oauth/access_token") {
            body {
                form {
                    add("oauth_verifier" to verifier, *options)
                }
            }
        }.text().complete()

        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&user_id=(\\d+)&screen_name=(.+)$".toRegex()
        val (accessToken, accessTokenSecret, userId, screenName) = pattern.matchEntire(result.content)!!.destructured

        return AccessTokenResponse(accessToken, accessTokenSecret, userId.toLong(), screenName)
    }
}
