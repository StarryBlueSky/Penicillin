package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byNullableBanner

class UserProfileBanner(val json: JsonElement) {
    val _1080x360 by json["sizes"].byNullableBanner("1080x360")
    val _1500x500 by json.byNullableBanner("1500x500")
    val _300x100 by json.byNullableBanner("300x100")
    val _600x200 by json.byNullableBanner("600x200")
    val ipad by json.byNullableBanner
    val ipadRetina by json.byNullableBanner("ipad_retina")
    val mobile by json.byNullableBanner
    val mobileRetina by json.byNullableBanner("mobile_retina")
    val web by json.byNullableBanner
    val webRetina by json.byNullableBanner("web_retina")
}
