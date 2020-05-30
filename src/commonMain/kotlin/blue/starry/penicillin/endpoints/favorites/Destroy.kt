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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.favorites

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Favorites
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Status

/**
 * Note: favorites are now known as likes.
 * Unfavorites (un-likes) the Tweet specified in the ID parameter as the authenticating user. Returns the un-liked Tweet when successful.
 * The process invoked by this method is asynchronous. The immediately returned Tweet object may not indicate the resultant favorited status of the Tweet. A 200 OK response from this method will indicate whether the intended action was successful or not.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/post-favorites-destroy)
 *
 * @param id The numerical ID of the Tweet to like.
 * @param includeEntities The entities node will be omitted when set to false.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Favorites] endpoint instance.
 * @return [JsonObjectApiAction] for [Status] model.
 */
fun Favorites.destroy(
    id: Long,
    includeEntities: Boolean? = null,
    vararg options: Option
) = client.session.post("/1.1/favorites/destroy.json") {
    formBody(
        "id" to id,
        "include_entities" to includeEntities,
        *options
    )
}.jsonObject { Status(it, client) }
