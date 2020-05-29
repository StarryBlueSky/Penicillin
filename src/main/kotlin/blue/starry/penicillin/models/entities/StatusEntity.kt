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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package blue.starry.penicillin.models.entities

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.intList
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.modelList
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient

import blue.starry.penicillin.models.PenicillinModel

data class StatusEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val hashtags by modelList { HashtagEntity(it, client) }
    val media by modelList { MediaEntity(it, client) }
    val symbols by modelList { SymbolEntity(it, client) }
    val userMentions by modelList("user_mentions") { UserMentionEntity(it, client) }
    val urls by modelList { URLEntity(it, client) }

    data class HashtagEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val text by string
        val indices by intList
    }

    data class UserMentionEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val screenName by string("screen_name")
        val name by string
        val id by long
        val idStr by string("id_str")
        val indices by intList
    }

    data class SymbolEntity(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val text by string
        val indices by intList
    }
}
