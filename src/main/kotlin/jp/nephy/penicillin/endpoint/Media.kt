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


class Media(override val client: PenicillinClient): Endpoint {
    fun createMetadata(mediaId: Long, vararg options: Pair<String, String>)= client.session.postObject<Empty>("https://upload.twitter.com/1.1/media/metadata/create.json") {
        json("media_id" to mediaId.toString(), *options)
    }

    fun uploadStatus(mediaId: Long, mediaKey: String? = null)= client.session.getObject<Media>("https://upload.twitter.com/1.1/media/upload.json") {
        query("command" to "STATUS", "media_id" to mediaId, "media_key" to mediaKey)
    }

    fun uploadMediaFile(file: File, mediaType: MediaType, mediaCategory: MediaCategory? = null): ObjectAction<Media> {
        val binary = ByteArray(file.length().toInt())

        FileInputStream(file).use {
            it.read(binary)
        }

        return uploadMedia(binary, mediaType, mediaCategory)
    }

    fun uploadMedia(file: ByteArray, mediaType: MediaType, mediaCategory: MediaCategory? = null): ObjectAction<Media> {
        val maxSeparateByte = 5 * 1024 * 1024
        val totalBytes = file.size.toLong()

        val initResult = uploadInit(totalBytes, mediaType, mediaCategory).complete()

        val separateCount = totalBytes / maxSeparateByte + (if (totalBytes % maxSeparateByte > 0) 1 else 0)
        ByteArrayInputStream(file).use {
            for (i in 0 until separateCount) {
                val startByte = i * maxSeparateByte
                val size = if ((i + 1) * maxSeparateByte <= totalBytes) maxSeparateByte else (totalBytes - startByte).toInt()
                val data = ByteArray(size)

                it.read(data)

                uploadAppend(data, mediaType, i.toInt(), initResult.result.mediaId)
            }
        }

        return uploadFinalize(initResult.result.mediaId)
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
