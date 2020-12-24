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

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.int
import blue.starry.jsonkt.delegation.modelList
import blue.starry.jsonkt.delegation.nullableString
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient



public data class PremiumSearchCount(override val json: JsonObject, override val client: ApiClient): PremiumSearchModel {
    public val results: List<Count> by modelList { Count(it, client) }
    public val totalCount: Int by int
    override val next: String? by nullableString
    public val requestParameters: List<RequestParameters> by modelList { RequestParameters(it, client) }

    public data class Count(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val timePeriod: String by string
        public val count: Int by int
    }

    public data class RequestParameters(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val fromDate: String? by nullableString
        public val toDate: String? by nullableString
        public val bucketRaw: String? by nullableString
    }
}
