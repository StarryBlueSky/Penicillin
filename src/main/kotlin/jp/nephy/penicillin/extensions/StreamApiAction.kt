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

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.request.action.StreamApiAction
import jp.nephy.penicillin.core.response.StreamResponse
import jp.nephy.penicillin.core.streaming.StreamProcessor
import jp.nephy.penicillin.core.streaming.handler.*
import jp.nephy.penicillin.core.streaming.listener.*
import jp.nephy.penicillin.extensions.endpoints.TweetstormHandler
import jp.nephy.penicillin.extensions.endpoints.TweetstormListener

/**
 * Listens with handler.
 *
 * @param handler [StreamHandler] for [L].
 *
 * @return New [StreamProcessor] instance for [L] and [H].
 */
fun <L: StreamListener, H: StreamHandler<L>> StreamResponse<L, H>.listen(handler: H): StreamProcessor<L, H> {
    return StreamProcessor(client, this, handler)
}

/**
 * Listens with listener and default handler.
 *
 * @param listener [StreamListener].
 *
 * @return New [StreamProcessor] instance for [L] and [H].
 */
fun <L: StreamListener, H: StreamHandler<L>> StreamResponse<L, H>.listen(listener: L): StreamProcessor<L, H> {
    @Suppress("UNCHECKED_CAST")
    val handler = when (listener) {
        is UserStreamListener -> UserStreamHandler(client, listener)
        is SampleStreamListener -> SampleStreamHandler(client, listener)
        is FilterStreamListener -> FilterStreamHandler(client, listener)
        is LivePipelineListener -> LivePipelineHandler(client, listener)
        is TweetstormListener -> TweetstormHandler(client, listener)
        else -> null
    } as? H ?: throw IllegalArgumentException("Unsupported StreamListener: ${listener::class}")

    return listen(handler)
}

/**
 * Shorthand extension to `await().listen(listener)`.
 *
 * @param listener [StreamListener].
 *
 * @return New [StreamProcessor] instance for [L] and [H].
 */
suspend fun <L: StreamListener, H: StreamHandler<L>> StreamApiAction<L, H>.listen(listener: L): StreamProcessor<L, H> {
    return await().listen(listener)
}
