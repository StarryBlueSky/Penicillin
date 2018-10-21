@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byImmutableJsonObject
import jp.nephy.jsonkt.delegation.byNullableString
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class CardPlatform(override val json: ImmutableJsonObject): PenicillinModel {
    private val platform by immutableJsonObject
    private val device by platform.byImmutableJsonObject
    private val audience by platform.byImmutableJsonObject

    val deviceName by device.byString("name")
    val deviceVersion by device.byString("version")

    val audienceName by audience.byString("name")
    val audienceBucket by audience.byNullableString("bucket")
}
