package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.Recipe
import jp.nephy.penicillin.auth.AuthorizationType
import jp.nephy.penicillin.auth.OAuthAuthHandler
import jp.nephy.penicillin.credential.AccessToken
import jp.nephy.penicillin.credential.AccessTokenSecret
import jp.nephy.penicillin.credential.ConsumerKey
import jp.nephy.penicillin.credential.ConsumerSecret
import jp.nephy.penicillin.misc.HTTPMethod
import jp.nephy.penicillin.response.ResponseText
import java.net.URL

class OAuth(private val client: Client) {
    @POST
    fun getRequestTokenResponse(callbackUrl: String?=null, vararg options: Pair<String, String?>): ResponseText {
        return client.session.new()
                .type(AuthorizationType.OAuth1aRequestToken)
                .url("https://api.twitter.com/oauth/request_token")
                .callback(callbackUrl)
                .dataAsForm(*options)
                .post()
                .getResponseText()
    }

    @POST
    fun getRequestToken(callbackUrl: String?=null, vararg options: Pair<String, String?>): Pair<String, String> {
        var requestToken: String? = null
        var requestTokenSecret: String? = null

        getRequestTokenResponse(callbackUrl, *options).content.split("&").forEach {
            val (k, v) = it.split("=")
            when (k) {
                "oauth_token" -> requestToken = v
                "oauth_token_secret" -> requestTokenSecret = v
            }
        }
        return Pair(requestToken!!, requestTokenSecret!!)
    }

    fun getAuthorizeURL(accessToken: String, forceLogin: Boolean?=null, screenName: String?=null): URL {
        var url = "https://api.twitter.com/oauth/authorize?oauth_token=$accessToken"
        var params = ""
        if (forceLogin != null) {
            params += "force_login=$forceLogin"
        }
        if (screenName != null) {
            params += "screen_name=$screenName"
        }

        if (params.isNotEmpty()) {
            url += "?$params"
        }
        return URL(url)
    }

    fun getAuthenticateURL(accessToken: String, forceLogin: Boolean?=null, screenName: String?=null): URL {
        var url = "https://api.twitter.com/oauth/authenticate?oauth_token=$accessToken"
        var params = ""
        if (forceLogin != null) {
            params += "force_login=$forceLogin"
        }
        if (screenName != null) {
            params += "screen_name=$screenName"
        }

        if (params.isNotEmpty()) {
            url += "?$params"
        }
        return URL(url)
    }

    @POST
    fun getAccessTokenResponse(ck: ConsumerKey, cs: ConsumerSecret, requestToken: String, requestTokenSecret: String, verifier: String, vararg options: Pair<String, String?>): ResponseText {
        val url = "https://api.twitter.com/oauth/access_token"
        val data = mutableListOf("oauth_verifier" to verifier).apply {
            options.forEach {
                if (it.second != null) {
                    this.add(Pair(it.first, it.second!!))
                }
            }
        }

        return client.session.new()
                .type(AuthorizationType.NONE)
                .url(url)
                .header("Authorization" to  OAuthAuthHandler(ck, cs, AccessToken(requestToken), AccessTokenSecret(requestTokenSecret)).sign(HTTPMethod.POST, url, data))
                .dataAsForm(*data.toTypedArray())
                .post()
                .getResponseText()
    }

    @POST
    fun getAccessToken(ck: ConsumerKey, cs: ConsumerSecret, requestToken: String, requestTokenSecret: String, verifier: String, vararg options: Pair<String, String?>): Pair<AccessToken, AccessTokenSecret> {
        var at: AccessToken? = null
        var ats: AccessTokenSecret? = null
        getAccessTokenResponse(ck, cs, requestToken, requestTokenSecret, verifier, *options).content.split("&").forEach {
            val (k, v) = it.split("=")
            when (k) {
                "oauth_token" -> at = AccessToken(v)
                "oauth_token_secret" -> ats = AccessTokenSecret(v)
            }
        }

        return Pair(at!!, ats!!)
    }

    @POST @Recipe
    @Deprecated("this function should not be used in GUI application because it requires stdin.")
    fun getAccessTokenCLI(callbackUrl: String?=null, forceLogin: Boolean?=null, screenName: String?=null): Pair<AccessToken, AccessTokenSecret> {
        val ck = client.session.oauthrt!!.ck
        val cs = client.session.oauthrt!!.cs
        val (requestToken, requestTokenSecret) = getRequestToken(callbackUrl)

        val url = getAuthorizeURL(requestToken, forceLogin, screenName)
        println("$url\nInput PIN code: ")
        val pin = readLine()!!

        return getAccessToken(ck, cs, requestToken, requestTokenSecret, pin)
    }
}
