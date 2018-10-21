@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.immutableJsonArray
import jp.nephy.jsonkt.delegation.immutableJsonObject

data class HelpSettings(override val json: ImmutableJsonObject): PenicillinModel {
    private val versions by immutableJsonObject
    val featureSwitchesVersion by versions.byString("feature_switches")
    val experimentVersion by versions.byString("experiments")
    val settingsVersion by versions.byString("settings")

    val impressions by immutableJsonArray

    val config by immutableJsonObject
}
