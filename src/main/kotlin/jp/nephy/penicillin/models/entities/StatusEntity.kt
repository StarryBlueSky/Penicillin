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

package jp.nephy.penicillin.models.entities

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.intList
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.extensions.penicillinModelList
import jp.nephy.penicillin.models.PenicillinModel

data class StatusEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val hashtags by penicillinModelList<HashtagEntity>()
    val media by penicillinModelList<MediaEntity>()
    val symbols by penicillinModelList<SymbolEntity>()
    val userMentions by penicillinModelList<UserMentionEntity>("user_mentions")
    val urls by penicillinModelList<URLEntity>()

    data class HashtagEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val text by string
        val indices by intList
    }

    data class UserMentionEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val screenName by string("screen_name")
        val name by string
        val id by long
        val idStr by string("id_str")
        val indices by intList()
    }

    data class SymbolEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val text by string
        val indices by intList
    }
}
