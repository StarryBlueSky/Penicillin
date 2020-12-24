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

@file:Suppress("UNUSED")

package blue.starry.penicillin.endpoints.media

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.jsonObjectOf
import blue.starry.jsonkt.toJsonObject
import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.EmptyApiAction
import blue.starry.penicillin.core.request.jsonBody
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Media
import blue.starry.penicillin.endpoints.Option

/**
 * This endpoint can be used to provide additional information about the uploaded media_id. This feature is currently only supported for images and GIFs.
 * The request flow should be:
 * 1. Upload media using either the simple upload endpoint or the (preferred) chunked upload endpoint.
 * 2. Call this endpoint to attach additional metadata such as image alt text.
 * 3. Create Tweet with media_id(s) attached.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/media/upload-media/api-reference/post-media-metadata-create)
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Media] endpoint instance.
 * @return [EmptyApiAction].
 */
public fun Media.createMetadata(
    mediaId: Long,
    payload: JsonObject,
    vararg options: Option
): EmptyApiAction = client.session.post("/1.1/media/metadata/create.json", EndpointHost.MediaUpload) {
    parameters(*options)
    jsonBody((payload + jsonObjectOf("media_id" to mediaId.toString())).toJsonObject())
}.empty()
