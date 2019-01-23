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

package jp.nephy.penicillin.extensions.models.builder

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.jsonObjectOf
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.models.DirectMessage
import java.util.*

class CustomDirectMessageBuilder: JsonBuilder<DirectMessage> {
    override var json: JsonObject = jsonObjectOf(
        "created_at" to null,
        "entities" to jsonObjectOf(),
        "id" to null,
        "id_str" to null,
        "read" to false,
        "recipient" to null,
        "recipient_id" to null,
        "recipient_id_str" to null,
        "sender" to null,
        "sender_id" to null,
        "sender_id_str" to null,
        "sender_screen_name" to null,
        "text" to null
    )

    private var createdAt: Date? = null
    fun createdAt(date: Date? = null) {
        createdAt = date
    }

    private var read = false
    fun read() {
        read = true
    }

    private val recipientBuilder = CustomUserBuilder()
    fun recipient(builder: CustomUserBuilder.() -> Unit) {
        recipientBuilder.apply(builder)
    }

    private val senderBuilder = CustomUserBuilder()
    fun sender(builder: CustomUserBuilder.() -> Unit) {
        senderBuilder.apply(builder)
    }

    private lateinit var message: String
    fun text(text: () -> Any?) {
        message = text()?.toString().orEmpty()
    }

    private var entities = jsonObjectOf()
    fun entities(json: JsonObject) {
        entities = json
    }

    @UseExperimental(PenicillinExperimentalApi::class)
    override fun build(): DirectMessage {
        val id = generateId()
        val recipient = recipientBuilder.build()
        val sender = senderBuilder.build()

        update {
            it["text"] = message
            it["read"] = read
            it["created_at"] = createdAt.toCreatedAt()

            it["id"] = id
            it["id_str"] = id.toString()

            it["recipient"] = recipient.json
            it["recipient_id"] = recipient.id
            it["recipient_id_str"] = recipient.idStr

            it["sender"] = sender.json
            it["sender_id"] = sender.id
            it["sender_id_str"] = sender.idStr
            it["sender_screen_name"] = sender.screenName

            it["entities"] = entities
        }
        
        return json.parseModel()
    }
}
