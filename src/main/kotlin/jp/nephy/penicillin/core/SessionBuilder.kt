package jp.nephy.penicillin.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.features.HttpPlainText
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.http.Cookie
import io.ktor.http.CookieEncoding
import io.ktor.http.parseClientCookiesHeader
import jp.nephy.penicillin.core.auth.Credentials
import jp.nephy.penicillin.core.emulation.EmulationMode
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit

class SessionBuilder {
    private var credentialsBuilder: Credentials.Builder.() -> Unit = {}
    fun account(initializer: Credentials.Builder.() -> Unit) {
        credentialsBuilder = initializer
    }

    private var emulationMode: EmulationMode? = null
    fun emulate(mode: EmulationMode) {
        emulationMode = mode
    }

    private var skipEmulationChecking: Boolean? = null
    fun skipEmulationChecking() {
        skipEmulationChecking = true
    }

    private var maxRetries: Int? = null
    fun maxRetries(count: Int) {
        maxRetries = count
    }

    private var retryInterval: Long? = null
    private var retryIntervalUnit: TimeUnit? = null
    fun retry(interval: Long, unit: TimeUnit) {
        retryInterval = interval
        retryIntervalUnit = unit
    }

    private val cookies = mutableMapOf<String, MutableList<Cookie>>()
    fun cookie(host: String, header: String, encoding: CookieEncoding = CookieEncoding.BASE64_ENCODING) {
        parseClientCookiesHeader(header).map {
            cookie(host, Cookie(name = it.key, value = it.value, encoding = encoding))
        }
    }

    private var useCookie = false
    fun acceptCookie() {
        useCookie = true
    }

    fun cookie(host: String, cookie: Cookie) {
        acceptCookie()
        if (host !in cookies) {
            cookies[host] = mutableListOf(cookie)
        } else {
            cookies[host]!!.add(cookie)
        }
    }

    private fun HttpClientConfig<*>.initialize() {
        install(HttpPlainText) {
            defaultCharset = Charsets.UTF_8
        }
        if (useCookie) {
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage().also {
                    runBlocking {
                        for (pair in cookies) {
                            for (cookie in pair.value) {
                                it.addCookie(pair.key, cookie)
                            }
                        }
                    }
                }
            }
        }
    }

    private var httpClient: HttpClient? = null
    fun <T: HttpClientEngineConfig> httpClient(engineFactory: HttpClientEngineFactory<T>, useDefaultTransformers: Boolean = false, block: HttpClientConfig<T>.() -> Unit = {}) {
        httpClient = HttpClient(engineFactory, useDefaultTransformers) {
            initialize()
            block()
        }
    }

    internal fun build(): Session {
        val authorizationData = Credentials.Builder().apply(credentialsBuilder).build()
        val httpClient = httpClient ?: HttpClient { initialize() }

        return Session(httpClient, authorizationData, ClientOption(maxRetries ?: 3, retryInterval ?: 1L, retryIntervalUnit ?: TimeUnit.SECONDS, emulationMode ?: EmulationMode.None, skipEmulationChecking ?: false))
    }
}

data class ClientOption(val maxRetries: Int, val retryInterval: Long, val retryIntervalUnit: TimeUnit, val emulationMode: EmulationMode, val skipEmulationChecking: Boolean)
