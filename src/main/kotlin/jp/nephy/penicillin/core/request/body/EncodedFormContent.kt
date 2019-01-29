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
import jp.nephy.penicillin.core.emulation.EmulationMode
import kotlinx.coroutines.io.ByteWriteChannel
import kotlinx.coroutines.io.writeStringUtf8

class EncodedFormContent(val forms: Parameters): OutgoingContent.WriteChannelContent() {
    override val contentType: ContentType = ContentType.Application.FormUrlEncoded.withCharset(Charsets.UTF_8)

    override suspend fun writeTo(channel: ByteWriteChannel) {
        channel.writeStringUtf8(forms.formUrlEncode())
    }

    class Builder(private val emulationMode: EmulationMode) {
        private val forms = ParametersBuilder()

        fun add(key: String, value: Any?, mode: EmulationMode? = null) {
            if (mode != null && emulationMode != mode) {
                return
            }

            forms[key] = value?.toString() ?: return
        }

        fun add(vararg pairs: Pair<String, Any?>, emulationMode: EmulationMode? = null) {
            for ((first, second) in pairs) {
                add(first, second, emulationMode)
            }
        }

        internal fun build(): EncodedFormContent {
            return EncodedFormContent(forms.build())
        }
    }
}
