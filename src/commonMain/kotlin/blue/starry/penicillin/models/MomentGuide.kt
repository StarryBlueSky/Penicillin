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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient



data class MomentGuide(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    private val category by jsonObject
    val categoryId by category.byString("category_id")
    val categoryName by category.byString("name")
    val categoryUri by category.byString("uri")
    val impressionId by long("impression_id")
    val cursor by string("scroll_cursor")
    val modules by modelList { Module(it, client) }
    val trendModule by nullableModel { TrendModule(it, client) }

    data class Module(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val moduleType by string("module_type")
        val moments by modelList { Moment(it, client) }
    }

    data class TrendModule(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val metadata by model { TrendMetadata(it, client) }
        val trends by modelList { TrendType(it, client) }
    }
}