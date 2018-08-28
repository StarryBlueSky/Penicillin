package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel


class ProfileImageExtension(override val json: JsonObject): PenicillinModel {
    val mediaColor by json.byModel<MediaColor>()
}
