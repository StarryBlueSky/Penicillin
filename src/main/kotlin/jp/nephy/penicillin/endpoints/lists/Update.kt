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

package jp.nephy.penicillin.endpoints.lists

import jp.nephy.penicillin.core.request.action.EmptyApiAction
import jp.nephy.penicillin.core.request.formBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Lists
import jp.nephy.penicillin.endpoints.Option

/**
 * Updates the specified list. The authenticated user must own the list to be able to update it.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-update)
 * 
 * @param listId The numerical id of the list.
 * @param name The name for the list.
 * @param mode Whether your list is public or private. Values can be public or private . If no mode is specified the list will be public.
 * @param description The description to give the list.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
private fun Lists.update(
    listId: Long,
    name: String? = null,
    mode: ListVisibilityMode = ListVisibilityMode.Default,
    description: String? = null,
    vararg options: Option
) = update(listId, null, null, null, name, mode, description, *options)

/**
 * Updates the specified list. The authenticated user must own the list to be able to update it.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-update)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param name The name for the list.
 * @param mode Whether your list is public or private. Values can be public or private . If no mode is specified the list will be public.
 * @param description The description to give the list.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.updateByOwnerScreenName(
    slug: String,
    ownerScreenName: String,
    name: String? = null,
    mode: ListVisibilityMode = ListVisibilityMode.Default,
    description: String? = null,
    vararg options: Option
) = update(null, slug, ownerScreenName, null, name, mode, description, *options)

/**
 * Updates the specified list. The authenticated user must own the list to be able to update it.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-update)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param name The name for the list.
 * @param mode Whether your list is public or private. Values can be public or private . If no mode is specified the list will be public.
 * @param description The description to give the list.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.updateByOwnerId(
    slug: String,
    ownerId: Long,
    name: String? = null,
    mode: ListVisibilityMode = ListVisibilityMode.Default,
    description: String? = null,
    vararg options: Option
) = update(null, slug, null, ownerId, name, mode, description, *options)

private fun Lists.update(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    name: String? = null,
    mode: ListVisibilityMode = ListVisibilityMode.Default,
    description: String? = null,
    vararg options: Option
) = client.session.post("/1.1/lists/update.json") {
    formBody(
        "list_id" to listId,
        "slug" to slug,
        "owner_screen_name" to ownerScreenName,
        "owner_id" to ownerId,
        "name" to name,
        "mode" to mode,
        "description" to description,
        *options
    )
}.empty()
