/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient
object Help {
    data class Configuration(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val charactersReservedPerMedia by int("characters_reserved_per_media")
        val clientEventUrl by string("client_event_url")
        val dmTextCharacterLimit by int("dm_text_character_limit")
        val maxMediaPerUpload by int("max_media_per_upload")
        val nonUsernamePaths by stringList("non_username_paths")
        val photoSizeLimit by int("photo_size_limit")
        val photoSizes by model("photo_sizes") { Photo(it, client) }
        val shortUrlLength by int("short_url_length")
        val shortUrlLengthHttps by int("short_url_length_https")
    }

    data class Language(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val code by string
        val status by string
        val name by string
    }

    data class Privacy(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val privacy by string
    }

    data class Tos(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val tos by string
    }

    data class Settings(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val versions by jsonObject
        val featureSwitchesVersion by versions.byString("feature_switches")
        val experimentVersion by versions.byString("experiments")
        val settingsVersion by versions.byString("settings")
        val impressions by jsonArray
        val config by jsonObject
    }
}
