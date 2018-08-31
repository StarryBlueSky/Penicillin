package jp.nephy.penicillin.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.apache.Apache
import io.ktor.client.engine.apache.ApacheEngineConfig
import io.ktor.client.features.HttpPlainText
import jp.nephy.penicillin.core.auth.AuthorizationHandler
import jp.nephy.penicillin.core.auth.Credentials
import jp.nephy.penicillin.core.emulation.EmulationMode
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit

class SessionBuilder {
    private var credentialsBuilder: Credentials.Builder.() -> Unit = {}
    fun account(initializer: Credentials.Builder.() -> Unit) {
        credentialsBuilder = initializer
    }

    private var emulationMode = EmulationMode.None
    fun emulate(mode: EmulationMode) {
        emulationMode = mode
    }

    private var httpClientInitializer: suspend HttpClientConfig.() -> Unit = {}
    fun httpClient(initializer: suspend HttpClientConfig.() -> Unit) {
        httpClientInitializer = initializer
    }

    private var httpEngineInitializer: ApacheEngineConfig.() -> Unit = {}
    fun httpEngine(initializer: ApacheEngineConfig.() -> Unit) {
        httpEngineInitializer = initializer
    }

    private var maxRetries = 3
    fun maxRetries(count: Int) {
        maxRetries = count
    }

    private var retryInterval = 1L
    private var retryIntervalUnit = TimeUnit.SECONDS
    fun retryInterval(interval: Long, unit: TimeUnit) {
        retryInterval = interval
        retryIntervalUnit = unit
    }

    fun build(): Session {
        val authorizationData = Credentials.Builder().apply(credentialsBuilder).build()
        val httpClient = HttpClient(Apache.create(httpEngineInitializer)) {
            install(AuthorizationHandler) {
                bind(authorizationData)
            }
            install(HttpPlainText) {
                defaultCharset = Charsets.UTF_8
            }
            runBlocking {
                httpClientInitializer()
            }
        }

        return Session(httpClient, authorizationData, ClientOption(maxRetries, retryInterval, retryIntervalUnit, emulationMode))
    }
}

data class ClientOption(val maxRetries: Int, val retryInterval: Long, val retryIntervalUnit: TimeUnit, val emulationMode: EmulationMode)
