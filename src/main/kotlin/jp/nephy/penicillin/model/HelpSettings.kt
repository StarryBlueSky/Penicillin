package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.*
import com.google.gson.JsonElement

class HelpSettings(val json: JsonElement) {
    val featureSwitchesVersion by json["versions"].byString("feature_switches")
    val experimentVersion by json["versions"].byString("experiments")
    val settingsVersion by json["versions"].byString("settings")

    val impressions by json.byArray

    val config by json.byObject
}
