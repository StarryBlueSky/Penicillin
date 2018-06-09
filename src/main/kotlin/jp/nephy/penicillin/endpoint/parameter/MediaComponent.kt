package jp.nephy.penicillin.endpoint.parameter

import java.io.File
import java.util.*

data class MediaFileComponent(val file: File, val type: MediaType, val category: MediaCategory = MediaCategory.TweetVideo)

data class MediaComponent(val data: ByteArray, val type: MediaType, val category: MediaCategory = MediaCategory.TweetVideo) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaComponent

        if (! Arrays.equals(data, other.data)) return false
        if (type != other.type) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(data)
        result = 31 * result + type.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}
