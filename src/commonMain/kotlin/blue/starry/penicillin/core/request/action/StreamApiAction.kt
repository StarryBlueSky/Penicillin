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

package blue.starry.penicillin.core.request.action

import blue.starry.jsonkt.toJsonObject
import blue.starry.penicillin.core.request.ApiRequest
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.streaming.handler.*
import blue.starry.penicillin.core.streaming.listener.*
import blue.starry.penicillin.extensions.endpoints.TweetstormHandler
import blue.starry.penicillin.extensions.endpoints.TweetstormListener
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*

/**
 * The [ApiAction] that provides stream-able response.
 */
public class StreamApiAction<L: StreamListener, H: StreamHandler<L>>(
    /**
     * The [ApiClient].
     */
    public val client: ApiClient,

    /**
     * The [ApiRequest].
     */
    public val request: ApiRequest
) {
    /**
     * Listens with listener and default handler.
     *
     * @param listener [StreamListener].
     */
    public suspend fun listen(listener: L) {
        client.session.httpClient.request<HttpStatement>(request.builder.finalize()).execute {
            checkError(it.request, it)

            val channel = it.receive<ByteReadChannel>()
            handle(channel, listener)
        }
    }

    private suspend fun handle(channel: ByteReadChannel, listener: L) {
        @Suppress("UNCHECKED_CAST") val handler = when (listener) {
            is UserStreamListener -> UserStreamHandler(client, listener)
            is SampleStreamListener -> SampleStreamHandler(client, listener)
            is FilterStreamListener -> FilterStreamHandler(client, listener)
            is LivePipelineListener -> LivePipelineHandler(client, listener)
            is TweetstormListener -> TweetstormHandler(client, listener)
            else -> null
        } as? H ?: error("Unsupported StreamListener: ${listener::class}")

        try {
            listener.onConnect()

            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line() ?: continue
                val content = line.unescapeHTML()

                when {
                    content.startsWith('{') -> {
                        handler.handle(content.toJsonObject())
                    }
                    content.isBlank() -> {
                        listener.onHeartbeat()
                    }
                    else -> {
                        val length = content.toIntOrNull()
                        if (length != null) {
                            listener.onLength(length)
                        } else {
                            listener.onUnknownData(content)
                        }
                    }
                }

                listener.onRawData(content)
            }

            listener.onDisconnect(null)
        } catch (t: Throwable) {
            listener.onDisconnect(t)
        }
    }
}
