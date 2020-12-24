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

package blue.starry.penicillin.endpoints.livepipeline


import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.LivePipeline
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.LivePipelineSubscription

/**
 * Undocumented endpoint.
 *
 * @param ids Array of status id to track.
 * @param options Optional. Custom parameters of this request.
 * @receiver [LivePipeline] endpoint instance.
 * @return [JsonObjectApiAction] for [LivePipelineSubscription] model.
 */
public fun LivePipeline.update(
    ids: List<Long>,
    vararg options: Option
): JsonObjectApiAction<LivePipelineSubscription> = client.session.post("/1.1/live_pipeline/update_subscriptions") {
    emulationModes += EmulationMode.TwitterForiPhone

    formBody(
        "sub_topics" to ids.joinToString(",") { "/tweet_engagement/$it" },
        *options
    )
}.jsonObject { LivePipelineSubscription(it, client) }
