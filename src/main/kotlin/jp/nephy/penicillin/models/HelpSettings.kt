@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.byString
import jp.nephy.jsonkt.delegation.jsonArray
import jp.nephy.jsonkt.delegation.jsonObject

data class HelpSettings(override val json: JsonObject): PenicillinModel {
    private val versions by jsonObject
    val featureSwitchesVersion by versions.byString("feature_switches")
    val experimentVersion by versions.byString("experiments")
    val settingsVersion by versions.byString("settings")
    val impressions by jsonArray
    val config by jsonObject
}
