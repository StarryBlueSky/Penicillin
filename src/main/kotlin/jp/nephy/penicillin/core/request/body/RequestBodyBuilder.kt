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

import io.ktor.client.utils.EmptyContent
import jp.nephy.penicillin.core.emulation.EmulationMode

class RequestBodyBuilder(private val emulationMode: EmulationMode) {
    private var encodedForm: EncodedFormContent? = null
    fun form(builder: EncodedFormContent.Builder.() -> Unit) {
        encodedForm = EncodedFormContent.Builder(emulationMode).apply(builder).build()
    }

    private var multiPart: MultiPartContent? = null
    fun multiPart(builder: MultiPartContent.Builder.() -> Unit) {
        multiPart = MultiPartContent.Builder().apply(builder).build()
    }

    private var json: JsonTextContent? = null
    fun json(builder: JsonTextContent.Builder.() -> Unit) {
        json = JsonTextContent.Builder().apply(builder).build()
    }

    internal fun build(): Any {
        return encodedForm ?: multiPart ?: json ?: EmptyContent
    }
}
