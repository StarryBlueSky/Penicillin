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

package blue.starry.penicillin.endpoints.blocks

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Blocks
import blue.starry.penicillin.endpoints.EndpointOverloads
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.User

/**
 * Un-blocks the user specified in the ID parameter for the authenticating user. Returns the un-blocked user when successful. If relationships existed before the block was instantiated, they will not be restored.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/mute-block-report-users/api-reference/post-blocks-destroy)
 *
 * @param screenName Required. The screen name of the potentially blocked user. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param includeEntities Optional. The entities node will not be included when set to false.
 * @param skipStatus Optional. When set to either true, t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Blocks] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 * @see Blocks.destroyByUserId
 */
@EndpointOverloads
fun Blocks.destroyByScreenName(
    screenName: String,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = destroy(screenName, null, includeEntities, skipStatus, *options)

/**
 * Un-blocks the user specified in the ID parameter for the authenticating user. Returns the un-blocked user when successful. If relationships existed before the block was instantiated, they will not be restored.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/mute-block-report-users/api-reference/post-blocks-destroy)
 *
 * @param userId Required. The ID of the potentially blocked user. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param includeEntities Optional. The entities node will not be included when set to false.
 * @param skipStatus Optional. When set to either true, t or 1 statuses will not be included in the returned user objects.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Blocks] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 * @see Blocks.destroyByScreenName
 */
@EndpointOverloads
fun Blocks.destroyByUserId(
    userId: Long,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = destroy(null, userId, includeEntities, skipStatus, *options)

private fun Blocks.destroy(
    screenName: String? = null,
    userId: Long? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    vararg options: Option
) = client.session.post("/1.1/blocks/destroy.json") {
    formBody(
        "screen_name" to screenName,
        "user_id" to userId,
        "include_entities" to includeEntities,
        "skip_status" to skipStatus,
        *options
    )

}.jsonObject { User(it, client) }
