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

package jp.nephy.penicillin.endpoints.collections

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.formBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Collections
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.models.Collection

/**
 * Create a Collection owned by the currently authenticated user.
 * The API endpoint may refuse to complete the request if the authenticated user has exceeded the total number of allowed collections for their account.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/curate-a-collection/api-reference/post-collections-create)
 * 
 * @param name The title of the collection being created, in 25 characters or less.
 * @param description A brief description of this collection in 160 characters or fewer.
 * @param url A fully-qualified URL to associate with this collection.
 * @param timelineOrder Order Tweets chronologically or in the order they are added to a Collection.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Collections] endpoint instance.
 * @return [JsonObjectApiAction] for [Collection.Model] model.
 */
fun Collections.create(
    name: String,
    description: String? = null,
    url: String? = null,
    timelineOrder: CollectionTimelineOrder = CollectionTimelineOrder.Default,
    vararg options: Option
) = client.session.post("/1.1/collections/create.json") {
    formBody(
        "name" to name,
        "description" to description,
        "url" to url,
        "timeline_order" to timelineOrder.value,
        *options
    )

}.jsonObject<Collection.Model>()
