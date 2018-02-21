package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.JsonModel
import jp.nephy.jsonkt.byJsonArray
import jp.nephy.jsonkt.byJsonObject
import jp.nephy.jsonkt.byString

@Suppress("UNUSED")
class HelpSettings(override val json: JsonObject): JsonModel {
    val featureSwitchesVersion by json["versions"].byString("feature_switches")
    val experimentVersion by json["versions"].byString("experiments")
    val settingsVersion by json["versions"].byString("settings")

    val impressions by json.byJsonArray

    val config by json.byJsonObject
}
