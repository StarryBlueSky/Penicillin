package jp.nephy.penicillin.core.auth

import io.ktor.client.HttpClient
import io.ktor.client.features.HttpClientFeature
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.http.HttpHeaders
import io.ktor.http.encodeOAuth
import io.ktor.http.encodeURLParameter
import io.ktor.http.plus
import io.ktor.util.AttributeKey
import io.ktor.util.encodeBase64
import io.ktor.util.flattenForEach
import jp.nephy.penicillin.core.EncodedFormContent
import jp.nephy.penicillin.core.MultiPartContent

class AuthorizationHandler(private val credentials: Credentials) {
    class Configuration {
        private lateinit var credentials: Credentials
        fun bind(target: Credentials) {
            credentials = target
        }

        internal fun build(): AuthorizationHandler {
            return AuthorizationHandler(credentials)
        }
    }

    companion object Feature: HttpClientFeature<Configuration, AuthorizationHandler> {
        const val urlHeader = "X-PenicillinUrl"
        const val authorizationTypeHeader = "X-Penicillin-Authorization-Type"
        const val oauthCallbackUrlHeader = "X-Penicillin-OAuth-Callback-Url"

        override val key: AttributeKey<AuthorizationHandler> = AttributeKey("AuthorizationHandler")

        override fun prepare(block: Configuration.() -> Unit): AuthorizationHandler {
            return Configuration().apply(block).build()
        }

        override fun install(feature: AuthorizationHandler, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) { _ ->
                val headerKeys = context.headers.names()
                if (authorizationTypeHeader in headerKeys) {
                    if (HttpHeaders.Authorization !in headerKeys) {
                        val signature = when (AuthorizationType.valueOf(context.headers[authorizationTypeHeader].orEmpty())) {
                            AuthorizationType.OAuth1a -> {
                                val authorizationHeaderComponent = linkedMapOf(
                                        "oauth_signature" to null,
                                        "oauth_callback" to context.headers[oauthCallbackUrlHeader],
                                        "oauth_nonce" to OAuthUtil.randomUUID(),
                                        "oauth_timestamp" to OAuthUtil.currentEpochTime(),
                                        "oauth_consumer_key" to feature.credentials.consumerKey!!,
                                        "oauth_token" to feature.credentials.accessToken,
                                        "oauth_version" to "1.0",
                                        "oauth_signature_method" to "HMAC-SHA1"
                                )

                                val signatureParam = sortedMapOf<String, String>().apply {
                                    authorizationHeaderComponent.filterValues { it != null }.forEach {
                                        put(it.key, it.value)
                                    }
                                    if (context.body !is MultiPartContent) {
                                        val forms = (context.body as? EncodedFormContent)?.forms
                                        val params = if (forms != null) {
                                            context.url.parameters.build() + forms
                                        } else {
                                            context.url.parameters.build()
                                        }

                                        params.flattenForEach { key, value ->
                                            put(key.encodeURLParameter(), value.encodeURLParameter())
                                        }
                                    }
                                }
                                val signatureParamString = OAuthUtil.signatureParamString(signatureParam)

                                val signingKey = OAuthUtil.signingKey(feature.credentials.consumerSecret!!, feature.credentials.accessTokenSecret)
                                val signatureBaseString = OAuthUtil.signingBaseString(context.method, context.headers[urlHeader].orEmpty(), signatureParamString)
                                authorizationHeaderComponent["oauth_signature"] = OAuthUtil.signature(signingKey, signatureBaseString)

                                "OAuth ${authorizationHeaderComponent.filterValues { it != null }.toList().joinToString(", ") { "${it.first}=\"${it.second}\"" }}"
                            }
                            AuthorizationType.OAuth2 -> {
                                "Bearer ${feature.credentials.bearerToken!!.encodeOAuth()}"
                            }
                            AuthorizationType.OAuth2RequestToken -> {
                                "Basic ${"${feature.credentials.consumerKey!!.encodeOAuth()}:${feature.credentials.consumerSecret!!.encodeOAuth()}".encodeBase64()}"
                            }
                            AuthorizationType.None -> null
                        }

                        if (signature != null) {
                            context.headers.append(HttpHeaders.Authorization, signature)
                        }
                    }
                }

                context.headers.remove(urlHeader)
                context.headers.remove(authorizationTypeHeader)
                context.headers.remove(oauthCallbackUrlHeader)
            }
        }
    }
}
