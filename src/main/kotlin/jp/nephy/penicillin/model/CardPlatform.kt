package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byNullableString
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

class CardPlatform(val json: JsonElement) {
    val deviceName by json["platform"]["device"].byString("name")
    val deviceVersion by json["platform"]["device"].byString("version")

    val audienceName by json["platform"]["audience"].byString("name")
    val audienceBucket by json["platform"]["audience"].byNullableString("bucket")
}