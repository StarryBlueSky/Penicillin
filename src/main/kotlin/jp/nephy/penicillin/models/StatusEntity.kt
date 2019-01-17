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

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.intList
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string

data class StatusEntity(override val json: JsonObject): PenicillinModel {
    val hashtags by modelList<HashtagEntity>()
    val media by modelList<MediaEntity>()
    val symbols by modelList<SymbolEntity>()
    val userMentions by modelList<UserMentionEntity>(key = "user_mentions")
    val urls by modelList<URLEntity>()

    data class HashtagEntity(override val json: JsonObject): PenicillinModel {
        val text by string
        val indices by intList
    }

    data class UserMentionEntity(override val json: JsonObject): PenicillinModel {
        val screenName by string("screen_name")
        val name by string
        val id by long
        val idStr by string("id_str")
        val indices by intList()
    }

    data class SymbolEntity(override val json: JsonObject): PenicillinModel {
        val text by string
        val indices by intList
    }
}
