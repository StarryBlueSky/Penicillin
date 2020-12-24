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
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient


public data class Search(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val searchMetadata: SearchMetadata by model("search_metadata") { SearchMetadata(it, client) }
    public val statuses: List<Status> by modelList { Status(it, client) }

    public data class SearchMetadata(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val completedIn: Float by float("completed_in")
        public val count: Int by int
        public val maxId: Long by long("max_id")
        public val maxIdStr: String by string("max_id_str")
        public val nextResults: String? by nullableString("next_results")
        public val query: String by string
        public val refreshUrl: String? by nullableString("refresh_url")
        public val sinceId: Int by int("since_id")
        public val sinceIdStr: String by string("since_id_str")
    }
}
