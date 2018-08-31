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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
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

    private var httpClientInitializer: suspend HttpClientConfig.() -> Unit = {}
    fun httpClient(initializer: suspend HttpClientConfig.() -> Unit) {
        httpClientInitializer = initializer
    }

    private var httpEngineInitializer: ApacheEngineConfig.() -> Unit = {}
    fun httpEngine(initializer: ApacheEngineConfig.() -> Unit) {
        httpEngineInitializer = initializer
    }

    private var threadPoolExecutorInitializer: () -> ExecutorService = {
        Executors.newCachedThreadPool()
    }
    fun executor(initializer: () -> ExecutorService) {
        threadPoolExecutorInitializer = initializer
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

    internal fun build(): Session {
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

        return Session(threadPoolExecutorInitializer(), httpClient, authorizationData, ClientOption(maxRetries ?: 3, retryInterval ?: 1L, retryIntervalUnit ?: TimeUnit.SECONDS, emulationMode ?: EmulationMode.None))
    }
}

data class ClientOption(val maxRetries: Int, val retryInterval: Long, val retryIntervalUnit: TimeUnit, val emulationMode: EmulationMode)
