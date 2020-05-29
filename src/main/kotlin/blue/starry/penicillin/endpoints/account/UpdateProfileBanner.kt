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

package blue.starry.penicillin.endpoints.account

import io.ktor.client.request.forms.append
import io.ktor.utils.io.core.writeFully
import blue.starry.penicillin.core.request.action.EmptyApiAction
import blue.starry.penicillin.core.request.append
import blue.starry.penicillin.core.request.multiPartBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Account
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.media.MediaType

/**
 * Uploads a profile banner on behalf of the authenticating user. More information about sizing variations can be found in [User Profile Images and Banners](https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners) and [GET users/profile_banner](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/get-users-profile_banner).
 *
 * Profile banner images are processed asynchronously. The profile_banner_url and its variant sizes will not necessary be available directly after upload.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/post-account-update_profile_banner)
 *
 * @param file Required. The raw image data being uploaded as the user's new profile banner.
 * @param mediaType Required. The type of file.
 * @param width Optional. The width of the preferred section of the image being uploaded in pixels. Use with [height], [offsetLeft], and [offsetTop] to select the desired region of the image to use.
 * @param height Optional. The height of the preferred section of the image being uploaded in pixels. Use with [width], [offsetLeft], and [offsetTop] to select the desired region of the image to use.
 * @param offsetLeft Optional. The number of pixels by which to offset the uploaded image from the left. Use with [height], [width], and [offsetTop] to select the desired region of the image to use.
 * @param offsetTop Optional. The number of pixels by which to offset the uploaded image from the top. Use with [height], [width], and [offsetLeft] to select the desired region of the image to use.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Account] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Account.updateProfileBanner(
    file: ByteArray, mediaType: MediaType, width: Int? = null, height: Int? = null, offsetLeft: Int? = null, offsetTop: Int? = null, vararg options: Option
) = client.session.post("/1.1/account/update_profile_banner.json") {
    multiPartBody {
        append("banner", "blob", mediaType.contentType) {
            writeFully(file)
        }
        append(
            "width" to width,
            "height" to height,
            "offset_left" to offsetLeft,
            "offset_top" to offsetTop,
            *options
        )
    }
}.empty()
