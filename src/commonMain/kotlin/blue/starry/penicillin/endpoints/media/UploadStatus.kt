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

import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Media
import blue.starry.penicillin.endpoints.Option

/**
 * The STATUS command is used to periodically poll for updates of media processing operation. After the STATUS command response returns succeeded, you can move on to the next step which is usually create Tweet with media_id.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/media/upload-media/api-reference/get-media-upload-status)
 * 
 * @param mediaId The media_id returned from the INIT command.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Media] endpoint instance.
 * @return [JsonObjectApiAction] for [blue.starry.penicillin.models.Media] model.
 */
public fun Media.uploadStatus(
    mediaId: Long,
    mediaKey: String? = null,
    vararg options: Option
): JsonObjectApiAction<blue.starry.penicillin.models.Media> = client.session.get("/1.1/media/upload.json", EndpointHost.MediaUpload) {
    parameters(
        "command" to "STATUS",
        "media_id" to mediaId,
        "media_key" to mediaKey,
        *options
    )
}.jsonObject { blue.starry.penicillin.models.Media(it, client) }
