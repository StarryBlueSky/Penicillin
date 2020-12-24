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

package blue.starry.penicillin.models.entities

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.intList
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.modelList
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient

import blue.starry.penicillin.models.PenicillinModel

public data class StatusEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    public val hashtags: List<HashtagEntity> by modelList { HashtagEntity(it, client) }
    public val media: List<MediaEntity> by modelList { MediaEntity(it, client) }
    public val symbols: List<SymbolEntity> by modelList { SymbolEntity(it, client) }
    public val userMentions: List<UserMentionEntity> by modelList("user_mentions") { UserMentionEntity(it, client) }
    public val urls: List<URLEntity> by modelList { URLEntity(it, client) }

    public data class HashtagEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val text: String by string
        public val indices: List<Int> by intList
    }

    public data class UserMentionEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val screenName: String by string("screen_name")
        public val name: String by string
        public val id: Long by long
        public val idStr: String by string("id_str")
        public val indices: List<Int> by intList
    }

    public data class SymbolEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val text: String by string
        public val indices: List<Int> by intList
    }
}
