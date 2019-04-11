/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.core.session.config

import jp.nephy.penicillin.core.emulation.OfficialClient
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.core.session.ApiClientDsl
import jp.nephy.penicillin.core.session.SessionBuilder

/**
 * Initializes account configuration.
 */
@ApiClientDsl
fun SessionBuilder.account(block: Credentials.Builder.() -> Unit) {
    getOrPutBuilder {
        Credentials.Builder()
    }.apply(block)
}

/**
 * Represents your application and account credentials.
 */
data class Credentials(
    /**
     * Consumer key for your application.
     */
    val consumerKey: String?,

    /**
     * Consumer secret for your application.
     */
    val consumerSecret: String?,

    /**
     * Access token for your account.
     */
    val accessToken: String?,

    /**
     * Access token secret for your account.
     */
    val accessTokenSecret: String?,

    /**
     * Bearer token for your account.
     */
    val bearerToken: String?,

    /**
     * Known device token for your account.
     */
    val knownDeviceToken: String?
): SessionConfig {
    /**
     * Builder of [Credentials].
     */
    class Builder: SessionConfigBuilder<Credentials> {
        /**
         * Consumer key for your application.
         */
        var consumerKey: String? = null

        /**
         * Consumer secret for your application.
         */
        var consumerSecret: String? = null

        /**
         * Access token for your account.
         */
        var accessToken: String? = null

        /**
         * Access token secret for your account.
         */
        var accessTokenSecret: String? = null

        /**
         * Bearer token for your account.
         */
        var bearerToken: String? = null

        /**
         * Known device token for your account.
         * Used only when emulationMode is [OfficialClient.OAuth1a.TwitterForiPhone].
         */
        @PenicillinExperimentalApi
        var knownDeviceToken: String? = null

        @UseExperimental(PenicillinExperimentalApi::class)
        override fun build(): Credentials {
            return Credentials(consumerKey, consumerSecret, accessToken, accessTokenSecret, bearerToken, knownDeviceToken)
        }
    }
}

/**
 * Configures your application with consumer key and consumer secret.
 *
 * @param consumerKey Consumer key.
 * @param consumerSecret Consumer secret.
 */
fun Credentials.Builder.application(consumerKey: String, consumerSecret: String) {
    this.consumerKey = consumerKey
    this.consumerSecret = consumerSecret
}

/**
 * Configures your application with predefined OAuth 1.0a client.
 *
 * @param client Predefined OAuth 1.0a client.
 */
fun Credentials.Builder.application(client: OfficialClient.OAuth1a) {
    consumerKey = client.consumerKey
    consumerSecret = client.consumerSecret
}

/**
 * Configures your account with access token and access token secret.
 *
 * @param accessToken Access token.
 * @param accessTokenSecret Access token secret.
 */
fun Credentials.Builder.token(accessToken: String, accessTokenSecret: String) {
    this.accessToken = accessToken
    this.accessTokenSecret = accessTokenSecret
}

/**
 * Configures your account with bearer token.
 *
 * @param bearerToken Bearer token.
 */
fun Credentials.Builder.token(bearerToken: String) {
    this.bearerToken = bearerToken
}

/**
 * Configures your account with predefined OAuth 2 client.
 *
 * @param client Predefined OAuth 2 client.
 */
fun Credentials.Builder.token(client: OfficialClient.OAuth2) {
    bearerToken = client.bearerToken
}

internal fun SessionBuilder.createCredentials(): Credentials {
    return getOrPutBuilder {
        Credentials.Builder()
    }.build()
}
