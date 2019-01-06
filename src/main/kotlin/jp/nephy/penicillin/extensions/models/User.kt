@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.models

import jp.nephy.penicillin.models.CommonUser

val CommonUser.isLockedAccount: Boolean
    get() = profileInterstitialType == "fake_account"

fun CommonUser.profileImageUrlWithVariantSize(size: ProfileImageSize): String {
    return profileImageUrl.let {
        when (size) {
            ProfileImageSize.Normal -> it
            ProfileImageSize.Original -> {
                val format = it.split(".").last()
                "${it.dropLast(11)}.$format"
            }
            else -> {
                val format = it.split(".").last()
                "${it.dropLast(10)}${size.suffix}.$format"
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
                "${it.dropLast(11)}.$format"
            }
            else -> {
                val format = it.split(".").last()
                "${it.dropLast(10)}${size.suffix}.$format"
            }
        }
    }
}

fun CommonUser.profileBannerUrlWithVariantSize(size: ProfileBannerSize): String? {
    return profileBannerUrl?.let { "$profileBannerUrl/${size.suffix}" }
}

enum class ProfileImageSize(val suffix: String) {
    Normal("normal"), Bigger("bigger"), Mini("mini"), Original("")
}

enum class ProfileBannerSize(val suffix: String) {
    Normal("600x200"), Bigger("1500x500"), Mini("300x100"), Web("web"), WebRetina("web_retina"), IPad("ipad"), IPadRetina("ipad_retina"), Mobile("mobile"), MobileRetina("mobile_retina")
}
