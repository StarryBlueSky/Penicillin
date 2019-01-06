@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.session

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.features.HttpPlainText
import io.ktor.client.features.cookies.AcceptAllCookiesStorage
import io.ktor.client.features.cookies.HttpCookies
import io.ktor.client.features.cookies.addCookie
import io.ktor.http.Cookie
import io.ktor.util.KtorExperimentalAPI
import jp.nephy.penicillin.core.auth.Credentials
import jp.nephy.penicillin.core.emulation.EmulationMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class SessionBuilder {
    private var credentialsBuilder: Credentials.Builder.() -> Unit = {}
    fun account(initializer: Credentials.Builder.() -> Unit) {
        credentialsBuilder = initializer
    }

    var emulationMode = EmulationMode.None
    private var skipEmulationChecking = false
    fun skipEmulationChecking() {
        skipEmulationChecking = true
    }

    var maxRetries = 3
    private var retryInMillis = 1000L
    fun retry(interval: Long, unit: TimeUnit) {
        retryInMillis = unit.toMillis(interval)
    }

    private var defaultTimeoutInMillis = 5000L
    fun defaultTimeout(interval: Long, unit: TimeUnit) {
        defaultTimeoutInMillis = unit.toMillis(interval)
    }

    private var dispatcherConfigBuilder: DispatcherConfig.Builder.() -> Unit = {}
    fun dispatcher(builder: DispatcherConfig.Builder.() -> Unit) {
        dispatcherConfigBuilder = builder
    }

    data class DispatcherConfig(val coroutineContext: CoroutineContext, val connectionThreadsCount: Int?) {
        class Builder {
            var connectionThreadsCount: Int? = null
            var coroutineContext: CoroutineContext = Dispatchers.Default

            internal fun build(): DispatcherConfig {
                return DispatcherConfig(coroutineContext, connectionThreadsCount)
            }
        }
    }

    private var cookieConfigBuilder: CookieConfig.Builder.() -> Unit = {}
    fun cookie(builder: CookieConfig.Builder.() -> Unit) {
        cookieConfigBuilder = builder
    }

    data class CookieConfig(val acceptCookie: Boolean, val cookies: Map<String, List<Cookie>>) {
        class Builder {
            private var acceptCookie = false
            fun acceptCookie() {
                acceptCookie = true
            }

            private val cookies = mutableMapOf<String, MutableList<Cookie>>()
            fun addCookie(host: String, cookie: Cookie) {
                if (host !in cookies) {
                    cookies[host] = mutableListOf(cookie)
                } else {
                    cookies[host]!!.add(cookie)
                }
            }

            internal fun build(): CookieConfig {
                return CookieConfig(acceptCookie, cookies)
            }
        }
    }

    private var httpClientEngineFactory: HttpClientEngineFactory<*>? = null
    private var httpClientConfig: HttpClientConfig<*>.() -> Unit = {}
    @Suppress("UNCHECKED_CAST")
    fun <T: HttpClientEngineConfig> httpClient(engineFactory: HttpClientEngineFactory<T>, block: HttpClientConfig<T>.() -> Unit = {}) {
        httpClientEngineFactory = engineFactory
        httpClientConfig = block as HttpClientConfig<*>.() -> Unit
    }

    fun httpClient(block: HttpClientConfig<*>.() -> Unit = {}) {
        httpClientConfig = block
    }

    private var httpClient: HttpClient? = null
    fun httpClient(client: HttpClient) {
        httpClient = client
    }

    @UseExperimental(KtorExperimentalAPI::class)
    internal fun build(): Session {
        val cookieConfig = CookieConfig.Builder().apply(cookieConfigBuilder).build()
        val dispatcherConfig = DispatcherConfig.Builder().apply(dispatcherConfigBuilder).build()
        val httpClient = httpClient ?: if (httpClientEngineFactory != null) HttpClient(httpClientEngineFactory!!) else HttpClient()
        httpClient.config {
            install(HttpPlainText) {
                defaultCharset = Charsets.UTF_8
            }

            if (cookieConfig.acceptCookie) {
                install(HttpCookies) {
                    storage = AcceptAllCookiesStorage()

                    if (cookieConfig.cookies.isNotEmpty()) {
                        runBlocking {
                            for ((key, value) in cookieConfig.cookies) {
                                for (cookie in value) {
                                    storage.addCookie(key, cookie)
                                }
                            }
                        }
                    }
                }
            }

            if (dispatcherConfig.connectionThreadsCount != null) {
                engine {
                    threadsCount = dispatcherConfig.connectionThreadsCount
                }
            }

            httpClientConfig.invoke(this)
        }
        val authorizationData = Credentials.Builder().apply(credentialsBuilder).build()
        val logger = KotlinLogging.logger("Penicillin.Client")

        return Session(
            httpClient,
            dispatcherConfig.coroutineContext,
            logger,
            authorizationData,
            ClientOption(maxRetries, retryInMillis, defaultTimeoutInMillis, emulationMode, skipEmulationChecking)
        )
    }
}
