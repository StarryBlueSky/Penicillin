package jp.nephy.penicillin.api.result

import com.github.salomonbrys.kotson.byNullableObject
import com.google.gson.JsonElement

class UserProfileBannerModel(val json: JsonElement) {
    val sizes by json.byNullableObject // {"ipad": {"h": 313, "w": 626, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/ipad"}, "ipad_retina": {"h": 626, "w": 1252, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/ipad_retina"}, "web": {"h": 260, "w": 520, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/web"}, "web_retina": {"h": 520, "w": 1040, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/web_retina"}, "mobile": {"h": 160, "w": 320, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/mobile"}, "mobile_retina": {"h": 320, "w": 640, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/mobile_retina"}, "300x100": {"h": 100, "w": 300, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/300x100"}, "600x200": {"h": 200, "w": 600, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/600x200"}, "1500x500": {"h": 500, "w": 1500, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/1500x500"}, "1080x360": {"h": 360, "w": 1080, "url": "https://pbs.twimg.com/profile_banners/3257907193/1495459942/1080x360"}}
}
