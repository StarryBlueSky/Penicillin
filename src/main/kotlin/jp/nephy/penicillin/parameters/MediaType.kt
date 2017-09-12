package jp.nephy.penicillin.parameters

enum class MediaType {
    JPEG, PNG, GIF, WebP, MP4;

    fun toMIMEType(): String = when (this) {
        JPEG -> "image/jpeg"
        PNG -> "image/png"
        GIF -> "image/gif"
        WebP -> "image/webp"
        MP4 -> "video/mp4"
    }
}
