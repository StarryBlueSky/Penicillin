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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.endpoints.search


import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.parameters
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.PrivateEndpoint
import jp.nephy.penicillin.endpoints.Search
import jp.nephy.penicillin.endpoints.common.TweetMode
import jp.nephy.penicillin.models.SearchUniversal

/**
 * Undocumented endpoint.
 *
 * @param options Optional. Custom parameters of this request.
 * @receiver [Search] endpoint instance.
 * @return [JsonObjectApiAction] for [SearchUniversal] model.
 */
@PrivateEndpoint
fun Search.universal(
    query: String,
    modules: String? = null,
    resultType: SearchResultType = SearchResultType.Default,
    tweetMode: TweetMode = TweetMode.Default,
    vararg options: Option
) = client.session.get("/1.1/search/universal.json") {
    parameters(
        "q" to query,
        "modules" to modules,
        "result_type" to resultType,
        "tweet_mode" to tweetMode,
        *options
    )
}.jsonObject<SearchUniversal>()
