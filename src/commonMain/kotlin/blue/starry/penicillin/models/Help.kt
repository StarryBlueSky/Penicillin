/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.JsonArray

public object Help {
    public data class Configuration(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val charactersReservedPerMedia: Int by int("characters_reserved_per_media")
        public val clientEventUrl: String by string("client_event_url")
        public val dmTextCharacterLimit: Int by int("dm_text_character_limit")
        public val maxMediaPerUpload: Int by int("max_media_per_upload")
        public val nonUsernamePaths: List<String> by stringList("non_username_paths")
        public val photoSizeLimit: Int by int("photo_size_limit")
        public val photoSizes: Photo by model("photo_sizes") { Photo(it, client) }
        public val shortUrlLength: Int by int("short_url_length")
        public val shortUrlLengthHttps: Int by int("short_url_length_https")
    }

    public data class Language(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val code: String by string
        public val status: String by string
        public val name: String by string
    }

    public data class Privacy(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val privacy: String by string
    }

    public data class Tos(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val tos: String by string
    }

    public data class Settings(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        private val versions by jsonObject
        public val featureSwitchesVersion: String by versions.byString("feature_switches")
        public val experimentVersion: String by versions.byString("experiments")
        public val settingsVersion: String by versions.byString("settings")
        public val impressions: JsonArray by jsonArray
        public val config: kotlinx.serialization.json.JsonObject by jsonObject
    }
}
