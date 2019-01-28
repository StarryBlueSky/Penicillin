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

package jp.nephy.penicillin.extensions.models

import jp.nephy.penicillin.models.CommonUser

fun CommonUser.profileImageUrlWithVariantSize(size: ProfileImageSize): String {
    return profileImageUrl.let {
        when (size) {
            ProfileImageSize.Normal -> it
            ProfileImageSize.Original -> {
                val format = it.split(".").last()
                "${it.dropLast(8 + format.length)}.$format"
            }
            else -> {
                val format = it.split(".").last()
                "${it.dropLast(7 + format.length)}${size.suffix}.$format"
            }
        }
    }
}

fun CommonUser.profileImageUrlHttpsWithVariantSize(size: ProfileImageSize): String {
    return profileImageUrlHttps.let {
        when (size) {
            ProfileImageSize.Normal -> it
            ProfileImageSize.Original -> {
                val format = it.split(".").last()
                "${it.dropLast(8 + format.length)}.$format"
            }
            else -> {
                val format = it.split(".").last()
                "${it.dropLast(7 + format.length)}${size.suffix}.$format"
            }
        }
    }
}

fun CommonUser.profileBannerUrlWithVariantSize(size: ProfileBannerSize): String? {
    return profileBannerUrl?.let { "$profileBannerUrl/${size.suffix}" }
}

// Refer https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners
enum class ProfileImageSize(val suffix: String) {
    Normal("normal"), Bigger("bigger"), Mini("mini"), Original("")
}

enum class ProfileBannerSize(val suffix: String) {
    Normal("600x200"), Bigger("1500x500"), Mini("300x100"), Web("web"), WebRetina("web_retina"), IPad("ipad"), IPadRetina("ipad_retina"), Mobile("mobile"), MobileRetina("mobile_retina")
}
