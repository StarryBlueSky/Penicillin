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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions.endpoints

import jp.nephy.penicillin.core.request.action.DelegatedAction
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.Statuses
import jp.nephy.penicillin.endpoints.media
import jp.nephy.penicillin.endpoints.media.MediaComponent
import jp.nephy.penicillin.endpoints.media.uploadMedia
import jp.nephy.penicillin.endpoints.media.uploadStatus
import jp.nephy.penicillin.endpoints.statuses.create
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.models.Media
import jp.nephy.penicillin.models.Media.ProcessingInfo.State.Succeeded
import kotlinx.coroutines.*

fun Statuses.updateWithMedia(
    status: String,
    media: List<MediaComponent>,
    vararg options: Option
) = DelegatedAction(client) {
    val results = media.map {
        client.session.async {
            client.media.uploadMedia(it).await().awaitProcessing()
        }
    }.awaitAll()
    
    create(status, mediaIds = results.map { it.mediaId }, options = *options).await()
}

private const val mediaProcessTimeoutMillis = 60 * 1000L

@Throws(CancellationException::class)
suspend fun Media.awaitProcessing(timeoutMillis: Long? = null): Media {
    if (processingInfo == null) {
        return this
    }
    
    var result = this
    
    withTimeout(timeoutMillis ?: mediaProcessTimeoutMillis) {
        while (true) {
            delay(result.processingInfo?.checkAfterSecs?.times(1000)?.toLong() ?: client.session.option.retryInMillis)
            
            result = client.media.uploadStatus(mediaId, mediaKey).await().result
            
            if (result.processingInfo == null || result.processingInfo?.state == Succeeded) {
                break
            }
        }
    }
    
    return result
}
