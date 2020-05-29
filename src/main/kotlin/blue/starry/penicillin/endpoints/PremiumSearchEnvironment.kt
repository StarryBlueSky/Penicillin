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

package blue.starry.penicillin.endpoints

import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.core.session.ApiClientDsl
import blue.starry.penicillin.endpoints.search.SearchProduct

/**
 * Returns [PremiumSearchEnvironment] endpoint instance.
 * @param product Select either 30days or fullarchive.
 * @param label The label associated with your search developer environment.
 * @return New [PremiumSearchEnvironment] endpoint instance.
 * @receiver Current [ApiClient] instance.
 */
@ApiClientDsl
fun PremiumSearch.environment(
    product: SearchProduct,
    label: String
): PremiumSearchEnvironment = PremiumSearchEnvironment(client, product, label)

/**
 * Collection of api endpoints related to Premium Search API with product and a label.
 *
 * @constructor Creates new [PremiumSearch] endpoint instance.
 * @param client Current [ApiClient] instance.
 * @see ApiClient.premiumSearch
 */
data class PremiumSearchEnvironment(
    override val client: ApiClient,
    
    /**
     * Select either 30days or fullarchive.
     */
    val product: SearchProduct,

    /**
     * The label associated with your search developer environment.
     */
    val label: String
): Endpoint {
    internal val endpoint = "/1.1/tweets/search/${product.value}/$label"
}
