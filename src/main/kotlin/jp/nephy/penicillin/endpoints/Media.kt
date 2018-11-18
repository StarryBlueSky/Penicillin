@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.endpoints.parameters.MediaCategory
import jp.nephy.penicillin.endpoints.parameters.MediaType
import jp.nephy.penicillin.models.Media
import java.io.File
import kotlin.math.ceil

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
