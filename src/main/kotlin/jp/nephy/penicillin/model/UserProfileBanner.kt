package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class UserProfileBanner(val json: JsonElement) {
    val _1080x360 by json["sizes"].byModel<Banner?>("1080x360")
    val _1500x500 by json.byModel<Banner?>("1500x500")
    val _300x100 by json.byModel<Banner?>("300x100")
    val _600x200 by json.byModel<Banner?>("600x200")
    val ipad by json.byModel<Banner?>()
    val ipadRetina by json.byModel<Banner?>("ipad_retina")
    val mobile by json.byModel<Banner?>()
    val mobileRetina by json.byModel<Banner?>("mobile_retina")
    val web by json.byModel<Banner?>()
    val webRetina by json.byModel<Banner?>("web_retina")
}
