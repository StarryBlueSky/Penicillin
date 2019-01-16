@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.nullableModel
import jp.nephy.jsonkt.delegation.string

data class UserProfileBanner(override val json: JsonObject): PenicillinModel {
    val resolution1080x360 by nullableModel<Banner>(key = "1080x360")
    val resolution1500x500 by nullableModel<Banner>(key = "1500x500")
    val resolution300x100 by nullableModel<Banner>(key = "300x100")
    val resolution600x200 by nullableModel<Banner>(key = "600x200")
    val ipad by nullableModel<Banner>()
    val ipadRetina by nullableModel<Banner>(key = "ipad_retina")
    val mobile by nullableModel<Banner>()
    val mobileRetina by nullableModel<Banner>(key = "mobile_retina")
    val web by nullableModel<Banner>()
    val webRetina by nullableModel<Banner>(key = "web_retina")

    data class Banner(override val json: JsonObject): PenicillinModel {
        val h by int
        val w by int
        val url by string
    }
}
