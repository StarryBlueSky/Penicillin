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

@file:Suppress("UNUSED", "KDocMissingDocumentation")

package blue.starry.penicillin.models.cursor

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.nullableInt
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.models.PenicillinModel

public abstract class PenicillinCursorModel<T: Any>(final override val json: JsonObject, final override val client: ApiClient): PenicillinModel {
    public val nextCursor: Long by long("next_cursor")
    public val nextCursorStr: String by string("next_cursor_str")
    public val previousCursor: Long by long("previous_cursor")
    public val previousCursorStr: String by string("previous_cursor_str")
    public val totalCount: Int? by nullableInt("total_count")

    public abstract val items: List<T>
}
