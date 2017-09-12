package jp.nephy.penicillin

import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.auth.BasicAuthHandler
import jp.nephy.penicillin.auth.BearerAuthHandler
import jp.nephy.penicillin.auth.OAuthAuthHandler
import jp.nephy.penicillin.credential.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class Session(connectionTimeoutSec: Long=20, readTimeoutSec: Long=40) {
    val client = OkHttpClient.Builder().connectTimeout(connectionTimeoutSec, TimeUnit.SECONDS).readTimeout(readTimeoutSec, TimeUnit.SECONDS).build()!!

    var oauth: OAuthAuthHandler? = null
    var basic: BasicAuthHandler? = null
    var bearer: BearerAuthHandler? = null
    @MustBeCalled
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret) {
        oauth = OAuthAuthHandler(ck, cs, at, ats)
        basic = BasicAuthHandler(ck, cs)
    }
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret) {
        basic = BasicAuthHandler(ck, cs)
    }
    fun authenticate(token: BearerToken) {
        bearer = BearerAuthHandler(token)
    }

    @MustBeCalled
    fun new() = PenicillinRequest(this)
}
