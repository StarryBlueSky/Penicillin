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

package blue.starry.penicillin.extensions.models

import blue.starry.penicillin.models.CommonUser

/**
 * Returns profile image url with specified size.
 */
fun CommonUser.profileImageUrlWithVariantSize(size: ProfileImageSize): String {
    return when (size) {
        ProfileImageSize.Normal -> profileImageUrl
        ProfileImageSize.Original -> {
            val format = profileImageUrl.split(".").last()
            "${profileImageUrl.dropLast(8 + format.length)}.$format"
        }
        else -> {
            val format = profileImageUrl.split(".").last()
            "${profileImageUrl.dropLast(7 + format.length)}${size.suffix}.$format"
        }
    }
}

/**
 * Returns profile image url (HTTPS) with specified size.
 */
fun CommonUser.profileImageUrlHttpsWithVariantSize(size: ProfileImageSize): String {
    return when (size) {
        ProfileImageSize.Normal -> profileImageUrlHttps
        ProfileImageSize.Original -> {
            val format = profileImageUrlHttps.split(".").last()
            "${profileImageUrlHttps.dropLast(8 + format.length)}.$format"
        }
        else -> {
            val format = profileImageUrlHttps.split(".").last()
            "${profileImageUrlHttps.dropLast(7 + format.length)}${size.suffix}.$format"
        }
    }
}

/**
 * Returns profile banner url with specified size.
 */
fun CommonUser.profileBannerUrlWithVariantSize(size: ProfileBannerSize): String? {
    return profileBannerUrl?.let { "$profileBannerUrl/${size.suffix}" }
}

/**
 * Profile images (also known as avatars) are an important component of a Twitter account’s expression of identity. To upload a profile image on behalf of a user, use POST account / update_profile_image.
 *
 * ** Alternative image sizes for user profile images **
 *
 * Obtain a user’s most recent profile image, along with the other components comprising their identity on Twitter, from GET users/show. The user object contains the profile_image_url and profile_image_url_https fields. These fields will contain the resized “normal” variant of the user’s uploaded image. This “normal” variant is typically 48px by 48px.
 * By modifying the URL, it is possible to retrieve other variant sizings such as “bigger”, “mini”, and “original”. Consult the table below for more examples:
 *
 * ** Default profile images **
 *
 * Some users may not have uploaded a profile image. Users who have not uploaded a profile image can be identified by the default_profile_image field of their user object having a true value.
 * The profile_image_url and profile_image_url_https URLs provided for users in this case will indicate Twitter’s default profile photo, which is https://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png.
 * The table above can be used to determine how to retrieve different size variants of the default image.
 *
 * ** Outdated profile images **
 * If a 403 or 404 error is returned when trying to access a profile image, refresh the user object using GET users/show to retrieve the most recent profile_image_url or profile_image_url_https. The URL may have changed, which happens for instance when the user updates their profile image.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners)
 */
enum class ProfileImageSize(internal val suffix: String?) {
    /**
     * 48x48.
     *
     * Example URL:
     *   http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png
     *   https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_normal.png
     */
    Normal("normal"),

    /**
     * 73x73.
     *
     * Example URL:
     *   http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_bigger.png
     *   https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_bigger.png
     */
    Bigger("bigger"),

    /**
     * 24x24.
     *
     * Example URL:
     *   http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_mini.png
     *   https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3_mini.png
     */
    Mini("mini"),

    /**
     * Original.
     *
     * Example URL:
     *   http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3.png
     *   https://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3.png
     *
     * Omit the underscore and variant to retrieve the original image. The images can be very large.
     */
    Original(null)
}

/**
 * Profile banners allow users to further customize the expressiveness of their profiles. To upload a profile banner on behalf of a user, use POST account / update_profile_banner.
 * Profile banners come in a variety of display-enhanced sizes. The variant sizes are available through a request to GET users / profile_banner or by modifying the final path component of the profile_banner_url found in a user object according to the table below.
 * The profile banner data available at each size variant’s URL is in PNG format.
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/user-profile-images-and-banners)
 */
enum class ProfileBannerSize(internal val suffix: String) {
    /**
     * 1500x500.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/1500x500
     */
    Bigger("1500x500"),

    /**
     * 600x200.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/600x200
     */
    Normal("600x200"),

    /**
     * 300x100.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/300x100
     */
    Mini("300x100"),

    /**
     * 520x260.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/web
     */
    Web("web"),

    /**
     * 1040x520.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/web_retina
     */
    WebRetina("web_retina"),

    /**
     * 626x313.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/ipad
     */
    IPad("ipad"),

    /**
     * 1252x626.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/ipad_retina
     */
    IPadRetina("ipad_retina"),

    /**
     * 320x160.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/mobile
     */
    Mobile("mobile"),

    /**
     * 640x320.
     *
     * Example URL:
     *   https://pbs.twimg.com/profile_banners/6253282/1431474710/mobile_retina
     */
    MobileRetina("mobile_retina")
}
