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

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.nullablePenicillinModel

data class Media(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val expiresAfterSecs by nullableInt("expires_after_secs")
    val mediaId by long("media_id")
    val mediaIdString by string("media_id_string")
    val mediaKey by nullableString("media_key")
    val processingInfo by nullablePenicillinModel<ProcessingInfo>("processing_info")
    val size by nullableInt
    val image by nullablePenicillinModel<Image>()
    val video by nullablePenicillinModel<Video>()

    data class ProcessingInfo(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val checkAfterSecs by nullableInt("check_after_secs")
        val error by nullablePenicillinModel<Error>()
        val progressPercent by nullableInt("progress_percent")
        val state by enum<String, State>()

        data class Error(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val code by int
            val name by nullableString
            val message by string
        }
        
        enum class State(override val value: String): StringJsonEnum {
            Pending("pending"), InProgress("in_progress"), Failed("failed"), Succeeded("succeeded")
        }
    }

    data class Image(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val imageType by string("image_type")
        val w by int
        val h by int
    }

    data class Video(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val videoType by string("video_type")
    }

    override fun equals(other: Any?): Boolean {
        return mediaId == (other as? Media)?.mediaId
    }

    override fun hashCode(): Int {
        return mediaId.hashCode()
    }
}
