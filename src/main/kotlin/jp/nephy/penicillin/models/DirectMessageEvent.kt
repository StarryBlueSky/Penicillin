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

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import jp.nephy.penicillin.core.session.ApiClient


object DirectMessageEvent {
    data class List(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val apps by model { App(it, client) }
        val events by modelList { Event(it, client) }
        val nextCursor by nullableString("next_cursor")

        data class App(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val id: String
                get() = json.keys.first()
            private val obj by json.byJsonObject(id)
            val name by obj.byString
            val url by obj.byString
        }

        data class Event(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            val createdTimestamp by string("created_timestamp")
            val id by string
            val messageCreate by model("message_create") { MessageCreate(it, client) }
            val type by string

            data class MessageCreate(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                val messageData by model("message_data") { MessageData(it, client) }
                val senderId by string("sender_id")
                val sourceAppId by nullableString("source_app_id")
                val target by model { Target(it, client) }

                data class MessageData(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    val entities by model { Entities(it, client) }
                    val text by string

                    data class Entities(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                        val hashtags by modelList { Hashtag(it, client) }
                        val symbols by jsonArray
                        val urls by modelList { Url(it, client) }
                        val userMentions by jsonArray("user_mentions")

                        data class Hashtag(override val json: JsonObject, override val client: ApiClient): IndexedEntityModel {
                            override val indices by intList
                            val text by string
                        }

                        data class Url(override val json: JsonObject, override val client: ApiClient): UrlEntityModel {
                            override val displayUrl by string("display_url")
                            override val expandedUrl by string("expanded_url")
                            override val indices by intList
                            override val url by string
                        }
                    }
                }

                data class Target(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    val recipientId by string("recipient_id")
                }
            }
        }
    }

    data class Show(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        val event by model { List.Event(it, client) }
    }
}
