package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byIntList
import jp.nephy.jsonkt.byString


class HashtagEntity(override val json: JsonObject): PenicillinModel {
    val text by json.byString
    val indices by json.byIntList
}
