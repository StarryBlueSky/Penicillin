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
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 * 
 * @param listId The numerical id of the list.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    listId: Long,
    userId: Long,
    vararg options: Option
) = addMember(listId, null, null, null, userId, null, *options)

/**
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 *
 * @param listId The numerical id of the list.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    listId: Long,
    screenName: String,
    vararg options: Option
) = addMember(listId, null, null, null, null, screenName, *options)

/**
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    slug: String,
    ownerScreenName: String,
    userId: Long,
    vararg options: Option
) = addMember(null, slug, ownerScreenName, null, userId, null, *options)

/**
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerScreenName The screen name of the user who owns the list being requested by a slug.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    slug: String,
    ownerScreenName: String,
    screenName: String,
    vararg options: Option
) = addMember(null, slug, ownerScreenName, null, null, screenName, *options)

/**
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    slug: String,
    ownerId: Long,
    userId: Long,
    vararg options: Option
) = addMember(null, slug, null, ownerId, userId, null, *options)

/**
 * Add a member to a list. The authenticated user must own the list to be able to add members to it. Note that lists cannot have more than 5,000 members.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/create-manage-lists/api-reference/post-lists-members-create)
 *
 * @param slug You can identify a list by its slug instead of its numerical id. If you decide to do so, note that you'll also have to specify the list owner using the owner_id or owner_screen_name parameters.
 * @param ownerId The user ID of the user who owns the list being requested by a slug.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Lists] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Lists.addMember(
    slug: String,
    ownerId: Long,
    screenName: String,
    vararg options: Option
) = addMember(null, slug, null, ownerId, null, screenName, *options)

private fun Lists.addMember(
    listId: Long? = null,
    slug: String? = null,
    ownerScreenName: String? = null,
    ownerId: Long? = null,
    userId: Long? = null,
    screenName: String? = null,
    vararg options: Option
) = client.session.post("/1.1/lists/members/create.json") {
    formBody(
        "list_id" to listId,
        "slug" to slug,
        "owner_screen_name" to ownerScreenName,
        "owner_id" to ownerId,
        "user_id" to userId,
        "screen_name" to screenName,
        *options
    )
}.empty()
