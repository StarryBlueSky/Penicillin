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

public data class CoverMedia(private val parentJson: JsonObject, private val parentClient: ApiClient): CommonCoverMedia(parentJson, parentClient)

public abstract class CommonCoverMedia(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    public val tweetId: String by string("tweet_id")
    public val type: String by string
    private val media by jsonObject
    public val mediaId: String by media.byString("media_id")
    public val mediaUrl: String by media.byString("url")
    private val size by media.byJsonObject
    public val mediaWidth: Int by size.byInt("w")
    public val mediaHeight: Int by size.byInt("h")
    private val render by jsonObject
    private val crops by render.byJsonObject
    public val renderCropSquare: FaceCoordinate by crops.byModel("square") { FaceCoordinate(it, client) }
    public val renderCropPortrait9to16: FaceCoordinate by crops.byModel("portrait_9_16") { FaceCoordinate(it, client) }
    public val renderCropPortrait3to4: FaceCoordinate by crops.byModel("portrait_3_4") { FaceCoordinate(it, client) }
    public val renderCropPortrait16to9: FaceCoordinate by crops.byModel("portrait_16_9") { FaceCoordinate(it, client) }
}
