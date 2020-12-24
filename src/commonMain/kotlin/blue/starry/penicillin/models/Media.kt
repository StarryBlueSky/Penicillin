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


public data class Media(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val expiresAfterSecs: Int? by nullableInt("expires_after_secs")
    public val mediaId: Long by long("media_id")
    public val mediaIdString: String by string("media_id_string")
    public val mediaKey: String? by nullableString("media_key")
    public val processingInfo: ProcessingInfo? by nullableModel("processing_info") { ProcessingInfo(it, client) }
    public val size: Int? by nullableInt
    public val image: Image? by nullableModel { Image(it, client) }
    public val video: Video? by nullableModel { Video(it, client) }

    public data class ProcessingInfo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val checkAfterSecs: Int? by nullableInt("check_after_secs")
        public val error: Error? by nullableModel { Error(it, client) }
        public val progressPercent: Int? by nullableInt("progress_percent")
        public val state: State by enum()

        public data class Error(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val code: Int by int
            public val name: String? by nullableString
            public val message: String by string
        }
        
        public enum class State(override val value: String): StringJsonEnum {
            Pending("pending"), InProgress("in_progress"), Failed("failed"), Succeeded("succeeded")
        }
    }

    public data class Image(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val imageType: String by string("image_type")
        public val w: Int by int
        public val h: Int by int
    }

    public data class Video(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val videoType: String by string("video_type")
    }

    override fun equals(other: Any?): Boolean {
        return mediaId == (other as? Media)?.mediaId
    }

    override fun hashCode(): Int {
        return mediaId.hashCode()
    }
}
