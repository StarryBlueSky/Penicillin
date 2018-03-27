package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.Recipe
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.model.Media
import jp.nephy.penicillin.parameters.MediaCategory
import jp.nephy.penicillin.parameters.MediaType
import jp.nephy.penicillin.response.ResponseObject
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream


class Media(private val client: Client) {
    @POST
    fun createMetadata(mediaId: Long, vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("https://upload.twitter.com/1.1/media/metadata/create.json")
                .dataAsJson("media_id" to mediaId.toString())
                .dataAsJson(*options)
                .post()
                .getResponseObject()
    }

    @GET
    fun uploadStatus(mediaId: Long, mediaKey: String?=null): ResponseObject<Media> {
        return client.session.new()
                .url("https://upload.twitter.com/1.1/media/upload.json")
                .param("command" to "STATUS")
                .param("media_id" to mediaId)
                .param("media_key" to mediaKey)
                .get()
                .getResponseObject()
    }

    @POST @Recipe
    fun uploadMediaFile(file: File, mediaType: MediaType, mediaCategory: MediaCategory?=null): ResponseObject<Media> {
        val binary = ByteArray(file.length().toInt())

        FileInputStream(file).use {
            it.read(binary)
        }

        return uploadMedia(binary, mediaType, mediaCategory)
    }

    @POST @Recipe
    fun uploadMedia(file: ByteArray, mediaType: MediaType, mediaCategory: MediaCategory?=null): ResponseObject<Media> {
        val maxSeparateByte = 5 * 1024 * 1024
        val totalBytes = file.size.toLong()

        val initResult = uploadInit(totalBytes, mediaType, mediaCategory)

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

    @POST
    fun uploadInit(totalBytes: Long, mediaType: MediaType, mediaCategory: MediaCategory?=null, additionalOwners: Array<Long>?=null, vararg options: Pair<String, String?>): ResponseObject<Media> {
        return client.session.new()
                .url("https://upload.twitter.com/1.1/media/upload.json")
                .dataAsForm("command" to "INIT")
                .dataAsForm("total_bytes" to totalBytes)
                .dataAsForm("media_type" to mediaType.toMIMEType())
                .dataAsForm("media_category" to when (mediaCategory) {
                    MediaCategory.TweetImage -> "tweet_image"
                    MediaCategory.TweetGif -> "tweet_gif"
                    MediaCategory.TweetVideo -> "tweet_video"
                    MediaCategory.AmplifyVideo -> "amplify_video"
                    else -> null
                })
                .dataAsForm("additional_owners" to additionalOwners?.joinToString(","))
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun uploadAppend(file: ByteArray, mediaType: MediaType, segmentIndex: Int, mediaId: Long, mediaKey: String?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("https://upload.twitter.com/1.1/media/upload.json")
                .dataAsForm("command" to "APPEND")
                .dataAsForm("media_id" to mediaId)
                .dataAsForm("media_key" to mediaKey)
                .dataAsForm("segment_index" to segmentIndex)
                .dataAsForm(*options)
                .file(file, mediaType.toMIMEType())
                .post()
                .getResponseObject()
    }

    @POST
    fun uploadFinalize(mediaId: Long, mediaKey: String?=null, vararg options: Pair<String, String?>): ResponseObject<Media> {
        return client.session.new()
                .url("https://upload.twitter.com/1.1/media/upload.json")
                .dataAsForm("command" to "FINALIZE")
                .dataAsForm("media_id" to mediaId)
                .dataAsForm("media_key" to mediaKey)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}
