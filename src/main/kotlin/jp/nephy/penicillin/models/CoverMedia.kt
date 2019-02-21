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
import jp.nephy.penicillin.extensions.byPenicillinModel

data class CoverMedia(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonCoverMedia(parentJson, parentClient)

abstract class CommonCoverMedia(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    val tweetId by string("tweet_id")
    val type by string
    private val media by jsonObject
    val mediaId by media.byString("media_id")
    val mediaUrl by media.byString("url")
    private val size by media.byJsonObject
    val mediaWidth by size.byInt("w")
    val mediaHeight by size.byInt("h")
    private val render by jsonObject
    private val crops by render.byJsonObject
    val renderCropSquare by crops.byPenicillinModel<FaceCoordinate>(client, "square")
    val renderCropPortrait9to16 by crops.byPenicillinModel<FaceCoordinate>(client, "portrait_9_16")
    val renderCropPortrait3to4 by crops.byPenicillinModel<FaceCoordinate>(client, "portrait_3_4")
    val renderCropPortrait16to9 by crops.byPenicillinModel<FaceCoordinate>(client, "portrait_16_9")
}
