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

package jp.nephy.penicillin.core.streaming.handler

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.parseObject
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.streaming.listener.FilterStreamListener
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream

import kotlinx.coroutines.launch

/**
 * Default FilterStream [StreamHandler].
 * Accepts listener of [FilterStreamListener].
 */
class FilterStreamHandler(override val client: ApiClient, override val listener: FilterStreamListener): StreamHandler<FilterStreamListener> {
    override fun handle(json: JsonObject) {
        launch {
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
        }

        launch {
            listener.onAnyJson(json)
        }
    }
}
