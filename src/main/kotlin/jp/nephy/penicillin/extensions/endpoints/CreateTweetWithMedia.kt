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
import jp.nephy.penicillin.endpoints.statuses.create
import jp.nephy.penicillin.extensions.await
import jp.nephy.penicillin.extensions.defer
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

fun Statuses.updateWithMediaFile(
    status: String,
    media: List<MediaFileComponent>,
    waitSecs: Long? = null,
    vararg options: Option
) = DelegatedAction(client) {
    val results = media.map {
        client.media.uploadMedia(it.file, it.type, it.category).defer()
    }.awaitAll()

    if (waitSecs != null) {
        // TODO: wait until media process completes
        delay(waitSecs * 1000)
    }
    
    create(status, mediaIds = results.map { it.mediaId }, options = *options).await().result
}

fun Statuses.updateWithMedia(
    status: String,
    media: List<MediaDataComponent>,
    waitSecs: Long? = null,
    vararg options: Option
) = DelegatedAction(client) {
    val results = media.map {
        client.media.uploadMedia(it.data, it.type, it.category).defer()
    }.awaitAll()

    if (waitSecs != null) {
        // TODO: wait until media process completes
        delay(waitSecs * 1000)
    }

    create(status, mediaIds = results.map { it.mediaId }, options = *options).await().result
}
