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

package blue.starry.penicillin.models

import blue.starry.jsonkt.JsonObject
import blue.starry.jsonkt.delegation.*
import blue.starry.penicillin.core.session.ApiClient
import kotlinx.serialization.json.JsonArray


public object DirectMessageEvent {
    public data class List(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val apps: App by model { App(it, client) }
        public val events: kotlin.collections.List<Event> by modelList { Event(it, client) }
        public val nextCursor: String? by nullableString("next_cursor")

        public data class App(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val id: String
                get() = json.keys.first()
            private val obj by json.byJsonObject(id)
            public val name: String by obj.byString
            public val url: String by obj.byString
        }

        public data class Event(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
            public val createdTimestamp: String by string("created_timestamp")
            public val id: String by string
            public val messageCreate: MessageCreate by model("message_create") { MessageCreate(it, client) }
            public val type: String by string

            public data class MessageCreate(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                public val messageData: MessageData by model("message_data") { MessageData(it, client) }
                public val senderId: String by string("sender_id")
                public val sourceAppId: String? by nullableString("source_app_id")
                public val target: Target by model { Target(it, client) }

                public data class MessageData(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    public val entities: Entities by model { Entities(it, client) }
                    public val text: String by string

                    public data class Entities(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                        public val hashtags: kotlin.collections.List<Hashtag> by modelList { Hashtag(it, client) }
                        public val symbols: JsonArray by jsonArray
                        public val urls: kotlin.collections.List<Url> by modelList { Url(it, client) }
                        public val userMentions: JsonArray by jsonArray("user_mentions")

                        public data class Hashtag(override val json: JsonObject, override val client: ApiClient): IndexedEntityModel {
                            override val indices: kotlin.collections.List<Int> by intList
                            public val text: String by string
                        }

                        public data class Url(override val json: JsonObject, override val client: ApiClient): UrlEntityModel {
                            override val displayUrl: String by string("display_url")
                            override val expandedUrl: String by string("expanded_url")
                            override val indices: kotlin.collections.List<Int> by intList
                            override val url: String by string
                        }
                    }
                }

                public data class Target(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
                    public val recipientId: String by string("recipient_id")
                }
            }
        }
    }

    public data class Show(override val json: JsonObject, override val client: ApiClient): PenicillinModel {
        public val event: List.Event by model { List.Event(it, client) }
    }
}
