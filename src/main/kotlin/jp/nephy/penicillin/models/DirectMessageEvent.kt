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
import jp.nephy.jsonkt.delegation.*

object DirectMessageEvent {
    data class List(override val json: JsonObject): PenicillinModel {
        val apps by model<App>()
        val events by modelList<Event>()
        val nextCursor by nullableString("next_cursor")

        data class App(override val json: JsonObject): PenicillinModel {
            val id: String
                get() = json.keys.first()
            val name by json[id].jsonObject.byString
            val url by json[id].jsonObject.byString
        }

        data class Event(override val json: JsonObject): PenicillinModel {
            val createdTimestamp by string("created_timestamp")
            val id by string
            val messageCreate by model<MessageCreate>(key = "message_create")
            val type by string

            data class MessageCreate(override val json: JsonObject): PenicillinModel {
                val messageData by model<MessageData>(key = "message_data")
                val senderId by string("sender_id")
                val sourceAppId by nullableString("source_app_id")
                val target by model<Target>()

                data class MessageData(override val json: JsonObject): PenicillinModel {
                    val entities by model<Entities>()
                    val text by string

                    data class Entities(override val json: JsonObject): PenicillinModel {
                        val hashtags by modelList<Hashtag>()
                        val symbols by jsonArray
                        val urls by modelList<Url>()
                        val userMentions by jsonArray("user_mentions")

                        data class Hashtag(override val json: JsonObject): PenicillinModel {
                            val indices by intList
                            val text by string
                        }

                        data class Url(override val json: JsonObject): PenicillinModel {
                            val displayUrl by string("display_url")
                            val expandedUrl by string("expanded_url")
                            val indices by intList
                            val url by string
                        }
                    }
                }

                data class Target(override val json: JsonObject): PenicillinModel {
                    val recipientId by string("recipient_id")
                }
            }
        }
    }

    data class Show(override val json: JsonObject): PenicillinModel {
        val event by model<DirectMessageEvent.List.Event>()
    }
}
