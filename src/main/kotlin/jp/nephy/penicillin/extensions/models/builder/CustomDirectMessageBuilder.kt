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
import jp.nephy.jsonkt.toJsonObject
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.extensions.parseModel
import jp.nephy.penicillin.models.DirectMessage
import java.util.*

/**
 * Custom payload builder for [DirectMessage].
 */
class CustomDirectMessageBuilder: JsonBuilder<DirectMessage>, JsonMap by jsonMapOf(
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
) {
    private var createdAt: Date? = null
    /**
     * Sets created_at.
     */
    fun createdAt(date: Date? = null) {
        createdAt = date
    }

    private var read = false
    /**
     * Sets read.
     */
    fun read() {
        read = true
    }

    private val recipientBuilder = CustomUserBuilder()
    /**
     * Sets recipient.
     */
    fun recipient(builder: CustomUserBuilder.() -> Unit) {
        recipientBuilder.apply(builder)
    }

    private val senderBuilder = CustomUserBuilder()
    /**
     * Sets sender.
     */
    fun sender(builder: CustomUserBuilder.() -> Unit) {
        senderBuilder.apply(builder)
    }

    private lateinit var message: String
    /**
     * Sets text.
     */
    fun text(text: () -> Any?) {
        message = text()?.toString().orEmpty()
    }

    private var entities = jsonObjectOf()
    /**
     * Sets entities.
     */
    fun entities(json: JsonObject) {
        entities = json
    }

    @UseExperimental(PenicillinExperimentalApi::class)
    override fun build(): DirectMessage {
        val id = generateId()
        val recipient = recipientBuilder.build()
        val sender = senderBuilder.build()
        
        this["text"] = message
        this["read"] = read
        this["created_at"] = createdAt.toCreatedAt()

        this["id"] = id
        this["id_str"] = id.toString()

        this["recipient"] = recipient.json
        this["recipient_id"] = recipient.id
        this["recipient_id_str"] = recipient.idStr

        this["sender"] = sender.json
        this["sender_id"] = sender.id
        this["sender_id_str"] = sender.idStr
        this["sender_screen_name"] = sender.screenName

        this["entities"] = entities
        
        return toJsonObject().parseModel()
    }
}
