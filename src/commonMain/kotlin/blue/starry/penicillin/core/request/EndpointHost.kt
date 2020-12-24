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

package blue.starry.penicillin.core.request

import io.ktor.http.*

/**
 * Represents endpoint host.
 */
public data class EndpointHost(
    /**
     * The domain.
     */
    public val domain: String,

    /**
     * The domain used in signing.
     */
    public val domainForSigning: String? = null,

    /**
     * The url protocol.
     */
    public val protocol: URLProtocol = URLProtocol.HTTPS,

    /**
     * The port.
     */
    public val port: Int = protocol.defaultPort
) {
    public companion object {
        /**
         * Default endpoint host.
         */
        public val Default: EndpointHost = EndpointHost("api.twitter.com")

        /**
         * Card endpoint host.
         */
        public val Card: EndpointHost = EndpointHost("caps.twitter.com")

        /**
         * Media upload endpoint.
         */
        public val MediaUpload: EndpointHost = EndpointHost("upload.twitter.com")

        /**
         * Publish endpoint.
         */
        public val Publish: EndpointHost = EndpointHost("publish.twitter.com")

        /* Stream Endpoints */

        /**
         * UserStream endpoint.
         */
        public val UserStream: EndpointHost = EndpointHost("userstream.twitter.com")

        /**
         * Stream endpoint.
         */
        public val Stream: EndpointHost = EndpointHost("stream.twitter.com")
    }
}
