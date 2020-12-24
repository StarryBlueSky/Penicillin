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

package blue.starry.penicillin.models.entities

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient


import blue.starry.penicillin.models.FaceCoordinate
import blue.starry.penicillin.models.PenicillinModel
import blue.starry.penicillin.models.Photo
import blue.starry.penicillin.models.UrlEntityModel

public data class MediaEntity(override val json: JsonObject, override val client: ApiClient): UrlEntityModel {
    public val additionalMediaInfo: AdditionalMediaInfo? by nullableModel("additional_media_info") { AdditionalMediaInfo(it, client) }
    override val displayUrl: String by string("display_url")
    override val expandedUrl: String by string("expanded_url")
    public val extAltText: String? by nullableString("ext_alt_text")
    public val features: Feature? by nullableModel { Feature(it, client) }
    public val id: Long by long
    public val idStr: String by string("id_str")
    override val indices: List<Int> by intList
    public val mediaUrl: String by string("media_url")
    public val mediaUrlHttps: String by string("media_url_https")
    public val sizes: Photo? by nullableModel { Photo(it, client) }
    public val sourceStatusId: Long? by nullableLong("source_status_id")
    public val sourceStatusIdStr: String? by nullableString("source_status_id_str")
    public val type: String by string
    override val url: String by string
    public val videoInfo: VideoInfo? by nullableModel("video_info") { VideoInfo(it, client) }

    public data class AdditionalMediaInfo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val title: String by string
        public val description: String by string
        public val embeddable: Boolean by boolean
    }

    public data class Feature(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val large: Size by model { Size(it, client) }
        public val medium: Size by model { Size(it, client) }
        public val orig: Size by model { Size(it, client) }
        public val small: Size by model { Size(it, client) }

        public data class Size(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val faces: List<FaceCoordinate> by modelList { FaceCoordinate(it, client) }
        }
    }

    public data class VideoInfo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val durationMillis: Int by int("duration_millis")
        public val aspectRatio: List<Int> by intList
        public val variants: List<Variant> by modelList { Variant(it, client) }

        public data class Variant(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val bitrate: Int? by nullableInt
            public val contentType: String by string("content_type")
            public val url: String by string
        }
    }
}
