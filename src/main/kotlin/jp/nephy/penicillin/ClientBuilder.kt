package jp.nephy.penicillin

import jp.nephy.penicillin.annotation.MustBeCalled
import jp.nephy.penicillin.credential.*

class ClientBuilder {
    private var ck: ConsumerKey? = null
    private var cs: ConsumerSecret? = null
    private var at: AccessToken? = null
    private var ats: AccessTokenSecret? = null
    private var token: BearerToken? = null

    private var useOfficialKeys: Boolean = false
    private var connectTimeout: Int? = null
    private var readTimeout: Int? = null
    private var writeTimeout: Int? = null

    @Suppress("UNUSED")
    fun officialClient(client: OfficialClient, at: AccessToken, ats: AccessTokenSecret) = this.apply {
        val keys = client.getCredentials()
        ck = keys.first
        cs = keys.second
        this@ClientBuilder.at = at
        this@ClientBuilder.ats = ats
        useOfficialKeys = true
    }
    @Suppress("UNUSED")
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret) = this.apply {
        this@ClientBuilder.ck = ck
        this@ClientBuilder.cs = cs
    }
    @Suppress("UNUSED")
    fun authenticate(ck: ConsumerKey, cs: ConsumerSecret, at: AccessToken, ats: AccessTokenSecret) = this.apply {
        this@ClientBuilder.ck = ck
        this@ClientBuilder.cs = cs
        this@ClientBuilder.at = at
        this@ClientBuilder.ats = ats
    }
    @Suppress("UNUSED")
    fun authenticate(token: BearerToken) = this.apply {
        this@ClientBuilder.token = token
    }

    @Suppress("UNUSED")
    fun connectTimeout(sec: Int) = this.apply {
        connectTimeout = sec
    }
    @Suppress("UNUSED")
    fun readTimeout(sec: Int) = this.apply {
        readTimeout = sec
    }
    @Suppress("UNUSED")
    fun writeTimeout(sec: Int) = this.apply {
        writeTimeout = sec
    }

    @MustBeCalled
    fun build(): Client {
        val session = Session(useOfficialKeys, connectTimeout, readTimeout, writeTimeout).apply {
            authenticate(ck, cs, at, ats, token)
        }

        return Client(session)
    }
}
