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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.stream


import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.request.action.StreamApiAction
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.streaming.handler.SampleStreamHandler
import jp.nephy.penicillin.core.streaming.listener.SampleStreamListener
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Stream

/**
 * Returns a small random sample of all public statuses via a stream. The Tweets returned by the default access level are the same, so if two different clients connect to this endpoint, they will see the same Tweets.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/sample-realtime/api-reference/get-statuses-sample)
 * 
 * @param delimited Specifies whether messages should be length-delimited. See [delimited](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/connecting) or more information.
 * @param stallWarnings Specifies whether stall warnings should be delivered. See [stall_warnings](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/connecting#stalls) for more information.
 * @param language Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Stream] endpoint instance.
 * @return [StreamApiAction] for [SampleStreamHandler] handler with [SampleStreamListener] listener.
 */
fun Stream.sample(
    delimited: StreamDelimitedBy? = null,
    stallWarnings: Boolean? = null,
    language: String? = null,
    vararg options: Option
) = client.session.get("/1.1/statuses/sample.json", EndpointHost.Stream) {
    parameter(
        "delimited" to delimited?.value,
        "stall_warning" to stallWarnings,
        "language" to language,
        *options
    )
}.stream<SampleStreamListener, SampleStreamHandler>()

/**
 * Shorthand property to [Stream.sample].
 * @see Stream.sample
 */
val Stream.sample
    get() = sample()
