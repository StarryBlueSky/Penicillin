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

@file:Suppress("UNUSED")

package blue.starry.penicillin.endpoints.help

import blue.starry.penicillin.core.request.action.JsonArrayApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Help
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Help.Language

/**
 * Returns the list of languages supported by Twitter along with the language code supported by Twitter.
 * The language code may be formatted as ISO 639-1 alpha-2 (en), ISO 639-3 alpha-3 (msa), or ISO 639-1 alpha-2 combined with an ISO 3166-1 alpha-2 localization (zh-tw).
 *
 * [Twitter API reference](https://developer.twitter.com/en/docs/developer-utilities/configuration/api-reference/get-help-configuration)
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Help] endpoint instance.
 * @return [JsonArrayApiAction] for [Language] model.
 */
public fun Help.languages(
    vararg options: Option
): JsonArrayApiAction<Language> = client.session.get("/1.1/help/languages.json") {
    parameters(*options)
}.jsonArray { Language(it, client) }

/**
 * Shorthand property to [Help.languages].
 * @see Help.languages
 */
public val Help.languages: JsonArrayApiAction<Language>
    get() = languages()
