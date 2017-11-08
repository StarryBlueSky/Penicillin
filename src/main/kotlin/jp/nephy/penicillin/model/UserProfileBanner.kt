package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

@Suppress("UNUSED")
class UserProfileBanner(val json: JsonElement) {
    val resolution1080x360 by json["sizes"].byModel<Banner?>("1080x360")
    val resolution1500x500 by json.byModel<Banner?>("1500x500")
    val resolution300x100 by json.byModel<Banner?>("300x100")
    val resolution600x200 by json.byModel<Banner?>("600x200")
    val ipad by json.byModel<Banner?>()
    val ipadRetina by json.byModel<Banner?>("ipad_retina")
    val mobile by json.byModel<Banner?>()
    val mobileRetina by json.byModel<Banner?>("mobile_retina")
    val web by json.byModel<Banner?>()
    val webRetina by json.byModel<Banner?>("web_retina")
}
