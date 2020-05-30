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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.media

import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Media
import blue.starry.penicillin.endpoints.Option

/**
 * The FINALIZE command should be called after the entire media file is uploaded using APPEND commands. If and (only if) the response of the FINALIZE command contains a processing_info field, it may also be necessary to use a [STATUS command](https://developer.twitter.com/en/docs/media/upload-media/api-reference/get-media-upload-status) and wait for it to return success before proceeding to Tweet creation.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/media/upload-media/api-reference/post-media-upload-finalize)
 * 
 * @param mediaId The media_id returned from the INIT command.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Media] endpoint instance.
 * @return [JsonObjectApiAction] for [blue.starry.penicillin.models.Media] model.
 */
fun Media.uploadFinalize(
    mediaId: Long,
    mediaKey: String? = null,
    vararg options: Option
) = client.session.post("/1.1/media/upload.json", EndpointHost.MediaUpload) {
    formBody(
        "command" to "FINALIZE",
        "media_id" to mediaId,
        "media_key" to mediaKey,
        *options
    )
}.jsonObject { blue.starry.penicillin.models.Media(it, client) }
