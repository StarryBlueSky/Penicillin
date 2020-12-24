/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

package blue.starry.penicillin.core.exceptions

import blue.starry.penicillin.core.i18n.LocalizedString
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * Base Penicillin exception class. All the exceptions are thrown from Penicillin inherits this class.
 */
public open class PenicillinException(
    /**
     * Original Localized string.
     */
    public val localizedString: LocalizedString,

    /**
     * The cause [Throwable], or null.
     */
    override val cause: Throwable?,

    /**
     * Executed [HttpRequest], or null.
     */
    public open val request: HttpRequest?,

    /**
     * Executed [HttpResponse], or null.
     */
    public open val response: HttpResponse?,

    /**
     * Optional vararg arguments for [localizedString].
     */
    vararg args: Any?
): RuntimeException() {
    public constructor(localizedString: LocalizedString): this(localizedString, null, null, null)

    override val message: String? = localizedString(*args)
}
