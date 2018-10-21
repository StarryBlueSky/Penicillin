@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class TimeZone(override val json: ImmutableJsonObject): PenicillinModel {
    val name by string
    val utcOffset by int("utc_offset")
    val tzinfoName by string("tzinfo_name")
}
