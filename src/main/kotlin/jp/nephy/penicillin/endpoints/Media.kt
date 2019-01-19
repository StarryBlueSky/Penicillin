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

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.request.action.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.parameters.MediaCategory
import jp.nephy.penicillin.endpoints.parameters.MediaType
import jp.nephy.penicillin.models.Media
import java.io.File
import kotlin.math.ceil

val PenicillinClient.media: jp.nephy.penicillin.endpoints.Media
    get() = Media(this)

class Media(override val client: PenicillinClient): Endpoint {
    fun createMetadata(mediaId: Long, vararg options: Pair<String, String>) = client.session.post("/1.1/media/metadata/create.json", EndpointHost.MediaUpload) {
        body {
            json {
                add("media_id" to mediaId.toString(), *options)
            }
        }
    }.empty()

    fun uploadStatus(mediaId: Long, mediaKey: String? = null) = client.session.get("/1.1/media/upload.json", EndpointHost.MediaUpload) {
        parameter("command" to "STATUS", "media_id" to mediaId, "media_key" to mediaKey)
    }.jsonObject<Media>()

    private val segmentMaxSize = 5 * 1024 * 1024
    fun uploadMedia(file: File, mediaType: MediaType, mediaCategory: MediaCategory? = null): PenicillinMultipleJsonObjectActions<Media> {
        return PenicillinMultipleJsonObjectActions.Builder {
            uploadInit(file.length(), mediaType, mediaCategory)
        }.also { builder ->
            file.inputStream().use {
                val segmentCount = ceil(it.available().toDouble() / segmentMaxSize).toInt()
                repeat(segmentCount) { i ->
                    val part = ByteArray(minOf(segmentMaxSize, it.available()))
                    it.read(part)
                    builder.request { results ->
                        uploadAppend(part, mediaType, i, results.first.result.mediaId)
                    }
                }
            }
        }.request { results ->
            uploadFinalize(results.first.result.mediaId)
        }.build()
    }

    fun uploadMedia(data: ByteArray, mediaType: MediaType, mediaCategory: MediaCategory? = null): PenicillinMultipleJsonObjectActions<Media> {
        return PenicillinMultipleJsonObjectActions.Builder {
            uploadInit(data.size.toLong(), mediaType, mediaCategory)
        }.also { builder ->
            data.inputStream().use {
                val segmentCount = ceil(it.available().toDouble() / segmentMaxSize).toInt()
                repeat(segmentCount) { i ->
                    val part = ByteArray(minOf(segmentMaxSize, it.available()))
                    it.read(part)
                    builder.request { results ->
                        uploadAppend(part, mediaType, i, results.first.result.mediaId)
                    }
                }
            }
        }.request { results ->
            uploadFinalize(results.first.result.mediaId)
        }.build()
    }

    private fun uploadInit(totalBytes: Long, mediaType: MediaType, mediaCategory: MediaCategory? = null, additionalOwners: List<Long>? = null, vararg options: Pair<String, Any?>) =
        client.session.post("/1.1/media/upload.json", EndpointHost.MediaUpload) {
            body {
                form {
                    add(
                        "command" to "INIT",
                        "total_bytes" to totalBytes,
                        "media_type" to mediaType.contentType,
                        "media_category" to mediaCategory?.value,
                        "additional_owners" to additionalOwners?.joinToString(","),
                        *options
                    )
                }
            }
        }.jsonObject<Media>()

    private fun uploadAppend(file: ByteArray, mediaType: MediaType, segmentIndex: Int, mediaId: Long, mediaKey: String? = null, vararg options: Pair<String, Any?>) =
        client.session.post("/1.1/media/upload.json", EndpointHost.MediaUpload) {
            body {
                multiPart {
                    add("media", "blob", mediaType.contentType, file)
                    add("command" to "APPEND", "media_id" to mediaId, "media_key" to mediaKey, "segment_index" to segmentIndex, *options)
                }
            }
        }.empty()

    private fun uploadFinalize(mediaId: Long, mediaKey: String? = null, vararg options: Pair<String, Any?>) = client.session.post("/1.1/media/upload.json", EndpointHost.MediaUpload) {
        body {
            form {
                add("command" to "FINALIZE", "media_id" to mediaId, "media_key" to mediaKey, *options)
            }
        }
    }.jsonObject<Media>()
}
