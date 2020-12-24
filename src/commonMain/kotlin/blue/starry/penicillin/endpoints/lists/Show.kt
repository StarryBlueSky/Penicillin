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

package blue.starry.penicillin.endpoints.lists

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Lists
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.TwitterList

/**
 * Returns the specified list. Private lists will only be shown if the authenticated user owns the specified list.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-show)
 * 
 * @param listId The numerical id of the list.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [JsonObjectApiAction] for [TwitterList] model.
 */
public fun Lists.show(
    listId: Long,
    vararg options: Option
): JsonObjectApiAction<TwitterList> = show(listId, null, null, null, *options)

/**
 * Returns the specified list. Private lists will only be shown if the authenticated user owns the specified list.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-show)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [JsonObjectApiAction] for [TwitterList] model.
 */
public fun Lists.showByOwnerScreenName(
    slug: String,
    ownerScreenName: String,
    vararg options: Option
): JsonObjectApiAction<TwitterList> = show(null, slug, ownerScreenName, null, *options)

/**
 * Returns the specified list. Private lists will only be shown if the authenticated user owns the specified list.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/get-lists-show)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [JsonObjectApiAction] for [TwitterList] model.
 */
public fun Lists.showByOwnerId(
    slug: String,
    ownerId: Long,
    vararg options: Option
): JsonObjectApiAction<TwitterList> = show(null, slug, null, ownerId, *options)

private fun Lists.show(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    vararg options: Option
) = client.session.get("/1.1/lists/show.json") {
    parameters(
        "list_id" to listId,
        "slug" to slug,
        "owner_screen_name" to ownerScreenName,
        "owner_id" to ownerId,
        *options
    )
}.jsonObject { TwitterList(it, client) }
