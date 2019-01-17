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

package jp.nephy.penicillin.core.request.body

import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.util.flattenEntries
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeFully
import kotlinx.coroutines.io.writeStringUtf8
import java.util.*

// From https://github.com/ktorio/ktor-samples/blob/183dd65e39565d6d09682a9b273937013d2124cc/other/client-multipart/src/MultipartApp.kt#L57
class MultiPartContent(private val parts: List<Part>): OutgoingContent.WriteChannelContent() {
    private val boundary = "***Penicillin-${UUID.randomUUID()}-Penicillin-${System.currentTimeMillis()}***"
    override val contentType = ContentType.MultiPart.FormData.withParameter("boundary", boundary).withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        for (part in parts) {
            channel.writeStringUtf8("--$boundary\r\n")
            val partHeaders = Headers.build {
                if (part.filename != null) {
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"${part.name}\"; filename=\"${part.filename}\"")
                } else {
                    append(HttpHeaders.ContentDisposition, "form-data; name=\"${part.name}\"")
                }
                appendAll(part.headers)
            }
            for ((key, value) in partHeaders.flattenEntries()) {
                channel.writeStringUtf8("$key: $value\r\n")
            }
            channel.writeStringUtf8("\r\n")
            part.writer(channel)
            channel.writeStringUtf8("\r\n")
        }
        channel.writeStringUtf8("--$boundary--\r\n")
    }

    data class Part(val name: String, val filename: String?, val headers: Headers = Headers.Empty, val writer: suspend ByteWriteChannel.() -> Unit)

    class Builder {
        private val parts = arrayListOf<Part>()

        private fun add(part: Part) {
            parts += part
        }

        fun add(name: String, filename: String, contentType: ContentType, headers: Headers = Headers.Empty, writer: suspend ByteWriteChannel.() -> Unit) {
            add(Part(name, filename, headers + headersOf(HttpHeaders.ContentType, contentType.toString()), writer))
        }

        fun add(name: String, filename: String, contentType: ContentType, data: ByteArray, headers: Headers = Headers.Empty) {
            add(name, filename, contentType, headers) {
                writeFully(data)
            }
        }

        fun add(name: String, text: String, contentType: ContentType? = null) {
            add(Part(name, null, headersOf(HttpHeaders.ContentType, contentType.toString())) { writeStringUtf8(text) })
        }

        fun add(vararg pairs: Pair<String, Any?>) {
            for ((first, second) in pairs) {
                add(first, second?.toString() ?: continue)
            }
        }

        internal fun build(): MultiPartContent {
            return MultiPartContent(parts.toList())
        }

        private operator fun Headers.plus(other: Headers): Headers {
            return when {
                isEmpty() -> other
                other.isEmpty() -> this
                else -> Headers.build {
                    appendAll(this@plus)
                    appendAll(other)
                }
            }
        }
    }
}
