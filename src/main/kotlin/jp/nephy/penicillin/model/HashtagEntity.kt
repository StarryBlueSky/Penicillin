package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byIntArray

class HashtagEntity(val json: JsonElement) {
    val text by json.byString
    val indices by json.byIntArray
}
