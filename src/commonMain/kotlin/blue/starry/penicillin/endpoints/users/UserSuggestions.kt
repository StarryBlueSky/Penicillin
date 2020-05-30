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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.users

import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.endpoints.Users
import blue.starry.penicillin.models.UserSuggestion

/**
 * Access the users in a given category of the Twitter suggested user list.
 * It is recommended that applications cache this data for no more than one hour.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-suggestions-slug)
 * 
 * @param slug The short name of list or a category
 * @param lang Restricts the suggested categories to the requested language. The language must be specified by the appropriate two letter ISO 639-1 representation. Currently supported languages are provided by the [GET help/languages](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-suggestions) API request. Unsupported language codes will receive English (en) results. If you use lang in this request, ensure you also include it when requesting the [GET users/suggestions/:slug](https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-suggestions) list.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Users] endpoint instance.
 * @return [JsonObjectApiAction] for [UserSuggestion] model.
 */
fun Users.userSuggestions(
    slug: String,
    lang: String? = null,
    vararg options: Option
) = client.session.get("/1.1/users/suggestions/$slug.json") {
    parameters(
        "lang" to lang,
        *options
    )
}.jsonObject { UserSuggestion(it, client) }
