package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byModel


class UserProfileBanner(override val json: JsonObject): JsonModel {
    val resolution1080x360 by json.byModel<Banner?>(key = "1080x360")
    val resolution1500x500 by json.byModel<Banner?>(key = "1500x500")
    val resolution300x100 by json.byModel<Banner?>(key = "300x100")
    val resolution600x200 by json.byModel<Banner?>(key = "600x200")
    val ipad by json.byModel<Banner?>()
    val ipadRetina by json.byModel<Banner?>(key = "ipad_retina")
    val mobile by json.byModel<Banner?>()
    val mobileRetina by json.byModel<Banner?>(key = "mobile_retina")
    val web by json.byModel<Banner?>()
    val webRetina by json.byModel<Banner?>(key = "web_retina")
}
