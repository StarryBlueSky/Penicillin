@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

object Help {
    data class Configuration(override val json: JsonObject): PenicillinModel {
        val charactersReservedPerMedia by int("characters_reserved_per_media")
        val clientEventUrl by string("client_event_url")
        val dmTextCharacterLimit by int("dm_text_character_limit")
        val maxMediaPerUpload by int("max_media_per_upload")
        val nonUsernamePaths by stringList("non_username_paths")
        val photoSizeLimit by int("photo_size_limit")
        val photoSizes by model<Photo>(key = "photo_sizes")
        val shortUrlLength by int("short_url_length")
        val shortUrlLengthHttps by int("short_url_length_https")
    }

    data class Language(override val json: JsonObject): PenicillinModel {
        val code by string
        val status by string
        val name by string
    }

    data class Privacy(override val json: JsonObject): PenicillinModel {
        val privacy by string
    }

    data class Tos(override val json: JsonObject): PenicillinModel {
        val tos by string
    }

    data class Settings(override val json: JsonObject): PenicillinModel {
        private val versions by jsonObject
        val featureSwitchesVersion by versions.byString("feature_switches")
        val experimentVersion by versions.byString("experiments")
        val settingsVersion by versions.byString("settings")
        val impressions by jsonArray
        val config by jsonObject
    }
}
