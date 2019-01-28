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
import jp.nephy.penicillin.core.streaming.handler.FilterStreamHandler
import jp.nephy.penicillin.core.streaming.listener.FilterStreamListener
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Stream

/**
 * Returns public statuses that match one or more filter predicates. Multiple parameters may be specified which allows most clients to use a single connection to the Streaming API. Both GET and POST requests are supported, but GET requests with too many parameters may cause the request to be rejected for excessive URL length. Use a POST request to avoid long URLs.
 * The track, follow, and locations fields should be considered to be combined with an OR operator. track=foo&follow=1234 returns Tweets matching "foo" OR created by user 1234.
 * The default access level allows up to 400 track keywords, 5,000 follow userids and 25 0.1-360 degree location boxes. If you need access to more rules and filtering tools, please apply for [enterprise access](https://developer.twitter.com/en/enterprise).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/filter-realtime/api-reference/post-statuses-filter)
 *
 * @param follow A comma separated list of user IDs, indicating the users to return statuses for in the stream. See [follow](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/basic-stream-parameters) for more information.
 * @param track Keywords to track. Phrases of keywords are specified by a comma-separated list. See [track](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/basic-stream-parameters) for more information.
 * @param locations Specifies a set of bounding boxes to track. See [locations](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/basic-stream-parameters) for more information.
 * @param delimited Specifies whether messages should be length-delimited. See [delimited](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/connecting) or more information.
 * @param stallWarnings Specifies whether stall warnings should be delivered. See [stall_warnings](https://developer.twitter.com/en/docs/tweets/filter-realtime/guides/connecting#stalls) for more information.
 * @param language Not documented yet.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Stream] endpoint instance.
 * @return [StreamApiAction] for [FilterStreamHandler] handler with [FilterStreamListener] listener.
 */
fun Stream.filter(
    follow: List<Long>? = null,
    track: List<String>? = null,
    locations: List<Pair<Double, Double>>? = null,
    delimited: StreamDelimitedBy? = null,
    stallWarnings: Boolean? = null,
    language: String? = null,
    vararg options: Option
) = client.session.get("/1.1/statuses/filter.json", EndpointHost.Stream) {
    parameter(
        "delimited" to delimited?.value,
        "stall_warning" to stallWarnings,
        "track" to track?.joinToString(","),
        "follow" to follow?.joinToString(","),
        "locations" to locations?.joinToString(",") { "${it.first},${it.second}" },
        "language" to language,
        *options
    )
}.stream<FilterStreamListener, FilterStreamHandler>()
