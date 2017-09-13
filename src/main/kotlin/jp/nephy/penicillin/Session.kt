package jp.nephy.penicillin

import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.auth.OAuth2RequestTokenHandler
import jp.nephy.penicillin.auth.OAuth2AuthHandler
import jp.nephy.penicillin.auth.OAuthAuthHandler
import jp.nephy.penicillin.auth.OAuthRequestTokenHandler
import jp.nephy.penicillin.credential.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class Session(val client: Client) {
    val httpClient = OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS).build()!!

    var oauth: OAuthAuthHandler? = null
    var oauthrt: OAuthRequestTokenHandler? = null
    var oauth2: OAuth2AuthHandler? = null
    var oauth2rt: OAuth2RequestTokenHandler? = null
    @MustBeCalled
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret) {
        oauth = OAuthAuthHandler(ck, cs, at, ats)
        oauthrt = OAuthRequestTokenHandler(ck, cs)
        oauth2rt = OAuth2RequestTokenHandler(ck, cs)
    }
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret) {
        oauthrt = OAuthRequestTokenHandler(ck, cs)
        oauth2rt = OAuth2RequestTokenHandler(ck, cs)
    }
    fun authenticate(token: BearerToken) {
        oauth2 = OAuth2AuthHandler(token)
    }

    @MustBeCalled
    fun new() = PenicillinRequest(this)
}
