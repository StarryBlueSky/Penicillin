@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byJsonObject
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.jsonObject

data class CardPlatform(override val json: JsonObject): PenicillinModel {
    private val platform by jsonObject
    private val device by platform.byJsonObject
    private val audience by platform.byJsonObject
    val deviceName by device.byString("name")
    val deviceVersion by device.byString("version")
    val audienceName by audience.byString("name")
    val audienceBucket by audience.byNullableString("bucket")
}
