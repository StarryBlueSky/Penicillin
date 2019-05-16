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

package jp.nephy.penicillin.endpoints.savedsearches


import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.core.request.formBody
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.endpoints.Option
import jp.nephy.penicillin.endpoints.SavedSearches
import jp.nephy.penicillin.models.SavedSearch

/**
 * Destroys a saved search for the authenticating user. The authenticating user must be the owner of saved search id being destroyed.
 * 
 * [Twitter API reference](https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/post-saved_searches-destroy-id)
 * 
 * @param id The ID of the saved search.
 * @param options Optional. Custom parameters of this request.
 * @receiver [SavedSearches] endpoint instance.
 * @return [JsonObjectApiAction] for [SavedSearch] model.
 */
fun SavedSearches.delete(
    id: Long,
    vararg options: Option
) = client.session.post("/1.1/saved_searches/destroy/$id.json") {
    formBody(*options)
}.jsonObject<SavedSearch>()
