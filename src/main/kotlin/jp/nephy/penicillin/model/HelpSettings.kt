package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byArray
import com.github.salomonbrys.kotson.byObject
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement

@Suppress("UNUSED")
class HelpSettings(val json: JsonElement) {
    val featureSwitchesVersion by json["versions"].byString("feature_switches")
    val experimentVersion by json["versions"].byString("experiments")
    val settingsVersion by json["versions"].byString("settings")

    val impressions by json.byArray

    val config by json.byObject
}
