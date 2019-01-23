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

package jp.nephy.penicillin.endpoints.account

import jp.nephy.penicillin.core.request.action.EmptyApiAction
import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Account
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.parameters.MediaType
import jp.nephy.penicillin.models.UserProfileBanner

/**
 * Returns a map of the available size variations of the specified user's profile banner. If the user has not uploaded a profile banner, a HTTP 404 will be served instead. This method can be used instead of string manipulation on the profile_banner_url returned in user objects as described in [Profile Images and Banners](https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners).
 * 
 * The profile banner data available at each size variant's URL is in PNG format.
 *
 * @param userId The ID of the user for whom to return results. Helpful for disambiguating when a valid user ID is also a valid screen name.
 * @param screenName The screen name of the user for whom to return results. Helpful for disambiguating when a valid screen name is also a user ID.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Account] endpoint instance.
 * @return [JsonObjectApiAction] for [UserProfileBanner] model.
 */
// TODO: overloads
fun Account.profileBanner(
    userId: Long? = null,
    screenName: String? = null,
    vararg options: Option
) = client.session.get("/1.1/users/profile_banner.json") {
    parameter(
        "user_id" to userId,
        "screen_name" to screenName,
        *options
    )
}.jsonObject<UserProfileBanner>()

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
    file: ByteArray,
    mediaType: MediaType,
    width: Int? = null,
    height: Int? = null,
    offsetLeft: Int? = null,
    offsetTop: Int? = null,
    vararg options: Option
) = client.session.post("/1.1/account/update_profile_banner.json") {
    body {
        multiPart {
            add("banner", "blob", mediaType.contentType, file)
            add(
                "width" to width,
                "height" to height,
                "offset_left" to offsetLeft,
                "offset_top" to offsetTop,
                *options
            )
        }
    }
}.empty()

/**
 * Removes the uploaded profile banner for the authenticating user. Returns HTTP 200 upon success.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/post-account-remove_profile_banner)
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Account] endpoint instance.
 * @return [EmptyApiAction].
 */
fun Account.removeProfileBanner(
    vararg options: Option
) = client.session.post("/1.1/account/remove_profile_banner.json") {
    body {
        form {
            add(*options)
        }
    }
}.empty()

/**
 * Shorthand property to [Account.removeProfileBanner].
 * @see Account.removeProfileBanner
 */
val Account.removeProfileBanner
    get() = removeProfileBanner()
