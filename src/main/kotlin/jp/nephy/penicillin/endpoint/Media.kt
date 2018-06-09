package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.MediaCategory
import jp.nephy.penicillin.endpoint.parameter.MediaType
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.model.Media
import jp.nephy.penicillin.request.ObjectAction
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import kotlin.math.ceil


class Media(override val client: PenicillinClient): Endpoint {
    fun createMetadata(mediaId: Long, vararg options: Pair<String, String>)= client.session.postObject<Empty>("https://upload.twitter.com/1.1/media/metadata/create.json") {
        json("media_id" to mediaId.toString(), *options)
    }

    fun uploadStatus(mediaId: Long, mediaKey: String? = null)= client.session.getObject<Media>("https://upload.twitter.com/1.1/media/upload.json") {
        query("command" to "STATUS", "media_id" to mediaId, "media_key" to mediaKey)
    }

    fun uploadMediaFile(file: File, mediaType: MediaType, mediaCategory: MediaCategory? = null): ObjectAction<Media> {
        val mediaId = uploadInit(file.length(), mediaType, mediaCategory).complete().result.mediaId
        uploadData(FileInputStream(file), mediaId, mediaType)

        return uploadFinalize(mediaId)
    }

    fun uploadMedia(data: ByteArray, mediaType: MediaType, mediaCategory: MediaCategory? = null): ObjectAction<Media> {
        val mediaId = uploadInit(data.size.toLong(), mediaType, mediaCategory).complete().result.mediaId
        uploadData(ByteArrayInputStream(data), mediaId, mediaType)

        return uploadFinalize(mediaId)
    }

    private val segmentMaxSize = 5 * 1024 * 1024
    private fun uploadData(stream: InputStream, mediaId: Long, mediaType: MediaType) {
        val segmentCount = ceil(1.0 * stream.available() / segmentMaxSize).toInt()
        val pool = ScheduledThreadPoolExecutor(segmentMaxSize, ThreadFactory {
            Executors.defaultThreadFactory().newThread(it).also {
                it.isDaemon = true
            }
        })
        stream.use {
            repeat(segmentCount) { i ->
                val data = ByteArray(minOf(segmentMaxSize, it.available()))
                it.read(data)
                pool.execute {
                    uploadAppend(data, mediaType, i, mediaId).complete()
                }
            }
        }

        pool.awaitTermination(30, TimeUnit.SECONDS)
    }

    private fun uploadInit(totalBytes: Long, mediaType: MediaType, mediaCategory: MediaCategory? = null, additionalOwners: List<Long>? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Media>("https://upload.twitter.com/1.1/media/upload.json") {
        form("command" to "INIT", "total_bytes" to totalBytes, "media_type" to mediaType.mimeType, "media_category" to mediaCategory?.value, "additional_owners" to additionalOwners?.joinToString(","), *options)
    }

    private fun uploadAppend(file: ByteArray, mediaType: MediaType, segmentIndex: Int, mediaId: Long, mediaKey: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Empty>("https://upload.twitter.com/1.1/media/upload.json") {
        form("command" to "APPEND", "media_id" to mediaId, "media_key" to mediaKey, "segment_index" to segmentIndex, *options)
        file(file, mediaType.mimeType, "media")
    }

    private fun uploadFinalize(mediaId: Long, mediaKey: String? = null, vararg options: Pair<String, Any?>)= client.session.postObject<Media>("https://upload.twitter.com/1.1/media/upload.json") {
        form("command" to "FINALIZE", "media_id" to mediaId, "media_key" to mediaKey, *options)
    }
}
