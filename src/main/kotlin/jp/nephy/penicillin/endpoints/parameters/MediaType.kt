package jp.nephy.penicillin.endpoints.parameters

import io.ktor.http.ContentType

enum class MediaType(val contentType: ContentType) {
    JPEG(ContentType.Image.JPEG),
    PNG(ContentType.Image.PNG),
    GIF(ContentType.Image.GIF),
    WebP(ContentType("image", "webp")),

    MP4(ContentType.Video.MP4)
}
