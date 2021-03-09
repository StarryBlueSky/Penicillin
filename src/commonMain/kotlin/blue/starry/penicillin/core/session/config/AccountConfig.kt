/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.core.session.config

import blue.starry.penicillin.core.emulation.OfficialClient
import blue.starry.penicillin.core.session.ApiClientDsl
import blue.starry.penicillin.core.session.SessionBuilder

/**
 * Initializes account configuration.
 */
@ApiClientDsl
public fun SessionBuilder.account(block: Credentials.Builder.() -> Unit) {
    getOrPutBuilder {
        Credentials.Builder()
    }.apply(block)
}

/**
 * Represents your application and account credentials.
 */
public data class Credentials(
    /**
     * Consumer key for your application.
     */
    public val consumerKey: String?,

    /**
     * Consumer secret for your application.
     */
    public val consumerSecret: String?,

    /**
     * Access token for your account.
     */
    public val accessToken: String?,

    /**
     * Access token secret for your account.
     */
    public val accessTokenSecret: String?,

    /**
     * Bearer token for your account.
     */
    public val bearerToken: String?,

    /**
     * Known device token for your account.
     */
    public val knownDeviceToken: String?
): SessionConfig {
    /**
     * Builder of [Credentials].
     */
    public class Builder: SessionConfigBuilder<Credentials> {
        /**
         * Consumer key for your application.
         */
        public var consumerKey: String? = null

        /**
         * Consumer secret for your application.
         */
        public var consumerSecret: String? = null

        /**
         * Access token for your account.
         */
        public var accessToken: String? = null

        /**
         * Access token secret for your account.
         */
        public var accessTokenSecret: String? = null

        /**
         * Bearer token for your account.
         */
        public var bearerToken: String? = null

        /**
         * Known device token for your account.
         * Used only when emulationMode is [OfficialClient.OAuth1a.TwitterForiPhone].
         */
        public var knownDeviceToken: String? = null

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
public fun Credentials.Builder.application(consumerKey: String, consumerSecret: String) {
    this.consumerKey = consumerKey
    this.consumerSecret = consumerSecret
}

/**
 * Configures your application with predefined OAuth 1.0a client.
 *
 * @param client Predefined OAuth 1.0a client.
 */
public fun Credentials.Builder.application(client: OfficialClient.OAuth1a) {
    consumerKey = client.consumerKey
    consumerSecret = client.consumerSecret
}

/**
 * Configures your account with access token and access token secret.
 *
 * @param accessToken Access token.
 * @param accessTokenSecret Access token secret.
 */
public fun Credentials.Builder.token(accessToken: String, accessTokenSecret: String) {
    this.accessToken = accessToken
    this.accessTokenSecret = accessTokenSecret
}

/**
 * Configures your account with bearer token.
 *
 * @param bearerToken Bearer token.
 */
public fun Credentials.Builder.token(bearerToken: String) {
    this.bearerToken = bearerToken
}

/**
 * Configures your account with predefined OAuth 2 client.
 *
 * @param client Predefined OAuth 2 client.
 */
public fun Credentials.Builder.token(client: OfficialClient.OAuth2) {
    bearerToken = client.bearerToken
}

internal fun SessionBuilder.createCredentials(): Credentials {
    return getOrPutBuilder {
        Credentials.Builder()
    }.build()
}
