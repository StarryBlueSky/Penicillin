package jp.nephy.penicillin.endpoints.parameters

import java.io.File

class MediaFileComponent(val file: File, val type: MediaType, val category: MediaCategory = MediaCategory.TweetVideo)

class MediaDataComponent(val data: ByteArray, val type: MediaType, val category: MediaCategory = MediaCategory.TweetVideo)
