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

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byNullableJsonObject
import jp.nephy.jsonkt.jsonObjectOrNull
import jp.nephy.jsonkt.stringOrNull
import jp.nephy.penicillin.core.streaming.listener.LivePipelineListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.intOrNull

class LivePipelineHandler(override val listener: LivePipelineListener): StreamHandler<LivePipelineListener> {
    override suspend fun handle(json: JsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            val topic = json.getOrNull("topic")?.stringOrNull ?: return@launch listener.onUnhandledJson(json)
            val id = topic.split("/").lastOrNull()?.toLongOrNull() ?: return@launch listener.onUnhandledJson(json)
            val payload by json.byNullableJsonObject
            val engagement = payload?.get("tweet_engagement")?.jsonObjectOrNull ?: return@launch listener.onUnhandledJson(json)
            if (topic.startsWith("/tweet_engagement/")) {
                when {
                    "like_count" in engagement -> {
                        listener.onUpdateLikeCount(id, engagement["like_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
                    }
                    "retweet_count" in engagement -> {
                        listener.onUpdateRetweetCount(id, engagement["retweet_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
                    }
                    "reply_count" in engagement -> {
                        listener.onUpdateReplyCount(id, engagement["reply_count"].intOrNull ?: return@launch listener.onUnhandledJson(json))
                    }
                    else -> {
                        listener.onUnhandledJson(json)
                    }
                }
            } else {
                listener.onUnhandledJson(json)
            }
        }

        listener.onAnyJson(json)
    }
}
