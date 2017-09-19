package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byModel

class ProfileImageExtension(val json: JsonElement) {
    val mediaColor by json.byModel<MediaColor>()
}
