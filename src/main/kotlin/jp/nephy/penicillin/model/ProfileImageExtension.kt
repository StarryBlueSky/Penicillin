package jp.nephy.penicillin.model

import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byMediaColor

class ProfileImageExtension(val json: JsonElement) {
    val mediaColor by json.byMediaColor
}
