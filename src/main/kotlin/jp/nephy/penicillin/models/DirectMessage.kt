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

@file:Suppress("UNUSED", "PublicApiImplicitType", "KDocMissingDocumentation")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.boolean
import jp.nephy.jsonkt.delegation.long
import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.PenicillinClient

data class DirectMessage(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
    // val createdAt by string("created_at")
    val entities by penicillinModel<StatusEntity>()
    val id by long
    val idStr by string("id_str")
    val read by boolean
    val recipient by penicillinModel<User>()
    val recipientId by long("recipient_id")
    val recipientIdStr by string("recipient_id_str")
    val recipientScreenName by string("recipient_screen_name")
    val sender by penicillinModel<User>()
    val senderId by long("sender_id")
    val senderIdStr by string("sender_id_str")
    val senderScreenName by string("sender_screen_name")
    val text by string
}
