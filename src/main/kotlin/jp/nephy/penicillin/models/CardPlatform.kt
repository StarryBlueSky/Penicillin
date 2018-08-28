package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byNullableString
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.get


class CardPlatform(override val json: JsonObject): PenicillinModel {
    val deviceName by json["platform"]["device"].byString("name")
    val deviceVersion by json["platform"]["device"].byString("version")

    val audienceName by json["platform"]["audience"].byString("name")
    val audienceBucket by json["platform"]["audience"].byNullableString("bucket")
}
