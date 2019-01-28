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

import jp.nephy.penicillin.endpoints.Media
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.media.uploadAppend
import jp.nephy.penicillin.endpoints.media.uploadMedia
import jp.nephy.penicillin.endpoints.media.MediaCategory
import jp.nephy.penicillin.endpoints.media.MediaType
import java.io.File
import java.nio.file.Path

fun Media.uploadAppend(
    data: ByteArray,
    mediaType: MediaType,
    mediaId: Long,
    segmentIndex: Int,
    vararg options: Option
) = uploadAppend(data.inputStream(), mediaType, mediaId, segmentIndex, *options)

fun Media.uploadAppend(
    file: File,
    mediaType: MediaType,
    mediaId: Long,
    segmentIndex: Int,
    vararg options: Option
) = uploadAppend(file.inputStream(), mediaType, mediaId, segmentIndex, *options)

fun Media.uploadAppend(
    path: Path,
    mediaType: MediaType,
    mediaId: Long,
    segmentIndex: Int,
    vararg options: Option
) = uploadAppend(path.toFile(), mediaType, mediaId, segmentIndex, *options)

fun Media.uploadMedia(
    data: ByteArray,
    mediaType: MediaType,
    mediaCategory: MediaCategory? = null
) = uploadMedia(data.inputStream(), mediaType, mediaCategory)

fun Media.uploadMedia(
    file: File,
    mediaType: MediaType,
    mediaCategory: MediaCategory? = null
) = uploadMedia(file.inputStream(), mediaType, mediaCategory)

fun Media.uploadMedia(
    path: Path,
    mediaType: MediaType,
    mediaCategory: MediaCategory? = null
) = uploadMedia(path.toFile(), mediaType, mediaCategory)
