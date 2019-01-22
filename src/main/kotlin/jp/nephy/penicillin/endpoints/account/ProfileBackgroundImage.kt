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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints.account

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Account
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.media
import jp.nephy.penicillin.endpoints.parameters.MediaType
import jp.nephy.penicillin.models.User

/**
 * Updates the authenticating user's profile background image. This method can also be used to enable or disable the profile background image.
 * Although each parameter is marked as optional, at least one of image or media_id must be provided when making this request.
 * Learn more about the deprecation of this endpoint via our [forum post](https://twittercommunity.com/t/upcoming-changes-to-the-developer-platform/104603).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/post-account-update_profile_background_image)
 *
 * @param data Required. The background image for the profile. Must be a valid GIF, JPG, or PNG image of less than 800 kilobytes in size. Images with width larger than 2048 pixels will be forcibly scaled down. The image must be provided as raw multipart data, not a URL.
 * @param mediaType Required. The type of file.
 * @param tile Optional. Whether or not to tile the background image. If set to true , t or 1 the background image will be displayed tiled. The image will not be tiled otherwise.
 * @param includeEntities Optional. The entities node will not be included when set to false.
 * @param skipStatus Optional. When set to either true, t or 1 statuses will not be included in the returned user object.
 * @param mediaId Specify the media to use as the background image. More information on upload of media is available [here](https://developer.twitter.com/rest/reference/post/media/upload).
 * @param options Optional. Custom parameters of this request.
 * @receiver [Account] endpoint instance.
 * @return [JsonObjectApiAction] for [User] model.
 */
@Deprecated("This endpoint is deprecated. See also https://twittercommunity.com/t/upcoming-changes-to-the-developer-platform/104603.")
fun Account.updateProfileBackgroundImage(
    data: ByteArray,
    mediaType: MediaType,
    tile: Boolean? = null,
    includeEntities: Boolean? = null,
    skipStatus: Boolean? = null,
    mediaId: Long? = null,
    vararg options: Option
) = client.media.uploadMedia(data, mediaType) + { results ->
    client.session.post("/1.1/account/update_profile_background_image.json") {
        body {
            form {
                add(
                    "tile" to tile,
                    "include_entities" to includeEntities,
                    "skip_status" to skipStatus,
                    "media_id" to (mediaId ?: results.first.result.mediaId),
                    *options
                )
            }
        }
    }.jsonObject<User>()
}
