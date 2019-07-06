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

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints.premiumsearch

import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.core.request.jsonBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.PremiumSearch
import jp.nephy.penicillin.endpoints.PremiumSearchEnvironment
import jp.nephy.penicillin.endpoints.environment
import jp.nephy.penicillin.endpoints.search.SearchBucket
import jp.nephy.penicillin.endpoints.search.SearchProduct
import jp.nephy.penicillin.models.PremiumSearchCount
import java.time.LocalDateTime

@PenicillinExperimentalApi
fun PremiumSearch.countWithLocalDateTime(
    product: SearchProduct,
    label: String,
    query: String,
    fromDate: LocalDateTime? = null,
    toDate: LocalDateTime? = null,
    bucket: SearchBucket? = null,
    next: String? = null,
    vararg options: Option
) = environment(product, label).countWithLocalDateTime(query, fromDate, toDate, bucket, next, *options)

@PenicillinExperimentalApi
fun PremiumSearch.count(
    product: SearchProduct,
    label: String,
    query: String,
    fromDate: String? = null,
    toDate: String? = null,
    bucket: SearchBucket? = null,
    next: String? = null,
    vararg options: Option
) = environment(product, label).count(query, fromDate, toDate, bucket, next, *options)

@PenicillinExperimentalApi
fun PremiumSearchEnvironment.countWithLocalDateTime(
    query: String,
    fromDate: LocalDateTime? = null,
    toDate: LocalDateTime? = null,
    bucket: SearchBucket? = null,
    next: String? = null,
    vararg options: Option
) = count(query, fromDate?.format(formatter), toDate?.format(formatter), bucket, next, *options)

@PenicillinExperimentalApi
fun PremiumSearchEnvironment.count(
    query: String,
    fromDate: String? = null,
    toDate: String? = null,
    bucket: SearchBucket? = null,
    next: String? = null,
    vararg options: Option
) = client.session.post("/1.1/tweets/search/${product.value}/$label/counts.json") {
    jsonBody(
        "query" to query,
        "fromDate" to fromDate,
        "toDate" to toDate,
        "bucket" to bucket?.value,
        "next" to next,
        *options
    )
}.environmentalJsonObject<PremiumSearchCount>(this)