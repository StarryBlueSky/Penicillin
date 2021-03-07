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

package blue.starry.penicillin.extensions.endpoints

import blue.starry.penicillin.core.exceptions.PenicillinTwitterMediaProcessingFailedError
import blue.starry.penicillin.core.request.action.ApiAction
import blue.starry.penicillin.core.response.JsonObjectResponse
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Statuses
import blue.starry.penicillin.endpoints.media
import blue.starry.penicillin.endpoints.media.MediaComponent
import blue.starry.penicillin.endpoints.media.uploadMedia
import blue.starry.penicillin.endpoints.media.uploadStatus
import blue.starry.penicillin.endpoints.statuses.create
import blue.starry.penicillin.extensions.DelegatedAction
import blue.starry.penicillin.models.Media
import blue.starry.penicillin.models.Media.ProcessingInfo.State.Succeeded
import blue.starry.penicillin.models.Status
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

/**
 * Creates new tweet with media.
 *
 * @param status Tweet text.
 * @param media A list of media.
 * @param options Optional. Custom parameters of this request.
 */
public fun Statuses.createWithMedia(
    status: String,
    media: List<MediaComponent>,
    vararg options: Option
): ApiAction<JsonObjectResponse<Status>> = DelegatedAction {
    val results = media.map {
        client.session.async {
            client.media.uploadMedia(it).execute().awaitProcessing()
        }
    }.awaitAll()
    
    create(status, mediaIds = results.map { it.mediaId }, options = options).execute()
}

private const val mediaProcessTimeoutMillis = 60 * 1000L

/**
 * Awaits until media processing is done, and returns [Media] response.
 * This operation is suspendable.
 *
 * @param timeoutMillis Timeout value in milliseconds.
 */
public suspend fun Media.awaitProcessing(timeoutMillis: Long? = null): Media {
    if (processingInfo == null) {
        return this
    }
    
    var result = this
    
    withTimeout(timeoutMillis ?: mediaProcessTimeoutMillis) {
        while (true) {
            delay(result.processingInfo?.checkAfterSecs?.times(1000)?.toLong() ?: client.session.option.retryInMillis)

            val response = client.media.uploadStatus(mediaId, mediaKey).execute()
            result = response.result

            if (result.processingInfo?.error != null && result.processingInfo?.state == Media.ProcessingInfo.State.Failed) {
                throw PenicillinTwitterMediaProcessingFailedError(result.processingInfo?.error!!, response.request, response.response)
            }

            if (result.processingInfo == null || result.processingInfo?.state == Succeeded) {
                break
            }
        }
    }
    
    return result
}
