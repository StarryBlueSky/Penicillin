package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.special.AccessTokenResponse
import jp.nephy.penicillin.model.special.RequestTokenResponse
import jp.nephy.penicillin.util.Util


class OAuth(override val client: PenicillinClient): Endpoint {
    fun requestToken(callbackUrl: String? = null, vararg options: Pair<String, Any?>): RequestTokenResponse {
        val result = client.session.postText("https://api.twitter.com/oauth/request_token") {
            callback(callbackUrl)
            form(*options)
        }.complete()

        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&oauth_callback_confirmed=(.+)$".toRegex()
        val (requestToken, requestTokenSecret, callbackConfirmed) = pattern.matchEntire(result.result) !!.destructured

        return RequestTokenResponse(requestToken, requestTokenSecret, callbackConfirmed.toBoolean())
    }

    fun authorizeUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        return Util.buildUrl("https://api.twitter.com/oauth/authorize", "oauth_token" to accessToken, "force_login" to forceLogin, "screen_name" to screenName)
    }

    fun authenticateUrl(accessToken: String, forceLogin: Boolean? = null, screenName: String? = null): String {
        return Util.buildUrl("https://api.twitter.com/oauth/authenticate", "oauth_token" to accessToken, "force_login" to forceLogin, "screen_name" to screenName)
    }

    fun accessToken(requestToken: String, requestTokenSecret: String, verifier: String, vararg options: Pair<String, Any?>): AccessTokenResponse {
        val result = PenicillinClient.build {
            application(client.session.consumerKey !!, client.session.consumerSecret !!)
            token(requestToken, requestTokenSecret)
        }.session.postText("https://api.twitter.com/oauth/access_token") {
            form("oauth_verifier" to verifier, *options)
        }.complete()

        val pattern = "^oauth_token=(.+)&oauth_token_secret=(.+)&user_id=(\\d+)&screen_name=(.+)$".toRegex()
        val (accessToken, accessTokenSecret, userId, screenName) = pattern.matchEntire(result.result) !!.destructured

        return AccessTokenResponse(accessToken, accessTokenSecret, userId.toLong(), screenName)
    }
}
