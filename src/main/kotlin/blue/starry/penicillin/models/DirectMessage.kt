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

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.boolean
import blue.starry.jsonkt.delegation.long
import blue.starry.jsonkt.delegation.model
import blue.starry.jsonkt.delegation.string
import blue.starry.penicillin.core.session.ApiClient
import blue.starry.penicillin.models.entities.StatusEntity

data class DirectMessage(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
    val createdAtRaw by string("created_at")
    val entities by model { StatusEntity(it, client) }
    val id by long
    val idStr by string("id_str")
    val read by boolean
    val recipient by model { User(it, client) }
    val recipientId by long("recipient_id")
    val recipientIdStr by string("recipient_id_str")
    val recipientScreenName by string("recipient_screen_name")
    val sender by model { User(it, client) }
    val senderId by long("sender_id")
    val senderIdStr by string("sender_id_str")
    val senderScreenName by string("sender_screen_name")
    val text by string

    override fun equals(other: Any?): Boolean {
        return id == (other as? DirectMessage)?.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
