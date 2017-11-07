package jp.nephy.penicillin

import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.auth.OAuth2AuthHandler
import jp.nephy.penicillin.auth.OAuth2RequestTokenHandler
import jp.nephy.penicillin.auth.OAuthAuthHandler
import jp.nephy.penicillin.auth.OAuthRequestTokenHandler
import jp.nephy.penicillin.credential.*
import jp.nephy.penicillin.auth.AuthorizationType
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import java.util.concurrent.TimeUnit

class Session(val useOfficialKeys: Boolean=false, connectTimeoutSec: Int?=null, readTimeoutSec: Int?=null, writeTimeoutSec: Int?=null) {
    val httpClient = OkHttpClient.Builder()
            .connectTimeout(connectTimeoutSec?.toLong() ?: 20, TimeUnit.SECONDS)
            .readTimeout(readTimeoutSec?.toLong() ?: 40, TimeUnit.SECONDS)
            .writeTimeout(writeTimeoutSec?.toLong() ?: 20, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            .connectionPool(ConnectionPool(10, 400, TimeUnit.SECONDS))
            .build()!!

    lateinit var authType: AuthorizationType
    var oauth: OAuthAuthHandler? = null
    var oauthrt: OAuthRequestTokenHandler? = null
    var oauth2: OAuth2AuthHandler? = null
    var oauth2rt: OAuth2RequestTokenHandler? = null
    @MustBeCalled
    fun authenticate(ck: ConsumerKey?=null, cs: ConsumerSecret?=null, at: AccessToken?=null, ats: AccessTokenSecret?=null, token: BearerToken?=null) {
        if (ck != null && cs != null && at != null && ats != null) {
            oauth = OAuthAuthHandler(ck, cs, at, ats)
        }
        if (ck != null && cs != null) {
            oauthrt = OAuthRequestTokenHandler(ck, cs)
            oauth2rt = OAuth2RequestTokenHandler(ck, cs)
        }
        if (token != null) {
            oauth2 = OAuth2AuthHandler(token)
        }

        authType = if (at != null && ats != null) {
            AuthorizationType.OAuth1a
        } else if (ck != null && cs != null) {
            AuthorizationType.OAuth1aRequestToken
        } else if (token != null) {
            AuthorizationType.OAuth2
        } else {
            throw IllegalArgumentException("ck, cs, at, ats, token are all null.")
        }
    }

    @MustBeCalled
    fun new() = PenicillinRequest(this)
}
