package jp.nephy.penicillin.core

import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import jp.nephy.penicillin.core.auth.Credentials
import jp.nephy.penicillin.endpoints.EndpointHost
import java.io.Closeable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Session(val httpClient: HttpClient, val credentials: Credentials, val option: ClientOption): Closeable {
    val executor = Executors.newCachedThreadPool()!!

    private fun call(method: HttpMethod, path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return PenicillinRequestBuilder(this, method, protocol, host, path).apply(builder).build()
    }

    fun get(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Get, path, host, protocol, builder)
    }

    fun post(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Post, path, host, protocol, builder)
    }

    fun put(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Put, path, host, protocol, builder)
    }

    fun patch(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Patch, path, host, protocol, builder)
    }

    fun delete(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Delete, path, host, protocol, builder)
    }

    fun head(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Head, path, host, protocol, builder)
    }

    fun options(path: String, host: EndpointHost = EndpointHost.Default, protocol: URLProtocol = URLProtocol.HTTPS, builder: PenicillinRequestBuilder.() -> Unit = {}): PenicillinRequest {
        return call(HttpMethod.Options, path, host, protocol, builder)
    }

    override fun close() {
        httpClient.close()
        executor.shutdownNow()
        executor.awaitTermination(10, TimeUnit.SECONDS)
    }
}
