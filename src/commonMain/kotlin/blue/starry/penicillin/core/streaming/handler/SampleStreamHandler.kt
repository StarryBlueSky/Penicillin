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

package blue.starry.penicillin.core.streaming.handler

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.parseObject
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.streaming.listener.SampleStreamListener
import blue.starry.penicillin.models.Status
import blue.starry.penicillin.models.Stream

/**
 * Default SampleStream [StreamHandler].
 * Accepts listener of [SampleStreamListener].
 */
public class SampleStreamHandler(override val client: ApiClient, override val listener: SampleStreamListener): StreamHandler<SampleStreamListener> {
    override suspend fun handle(json: JsonObject) {
        when {
            "text" in json -> {
                listener.onStatus(json.parseObject { Status(it, client) })
            }
            "delete" in json -> {
                listener.onDelete(json.parseObject { Stream.Delete(it, client) })
            }
            "warning" in json -> {
                listener.onWarning(json.parseObject { Stream.Warning(it, client) })
            }
            else -> {
                listener.onUnhandledJson(json)
            }
        }

        listener.onAnyJson(json)
    }
}
