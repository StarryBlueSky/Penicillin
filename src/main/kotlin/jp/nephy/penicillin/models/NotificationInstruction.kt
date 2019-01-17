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
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string

object NotificationInstruction {
    data class ClearCache(override val json: JsonObject): PenicillinModel

    data class AddEntries(override val json: JsonObject): PenicillinModel {
        val entities by modelList<Entity>()

        data class Entity(override val json: JsonObject): PenicillinModel {
            val content by model<Content>()  // {...}
            val entryId by string
            val sortIndex by string

            data class Content(override val json: JsonObject): PenicillinModel {
                val item by model<Item>()

                data class Item(override val json: JsonObject): PenicillinModel {
                    val clientEventInfo by model<ClientEventInfo>()  // {...}
                    val content by model<Content>()  // {...}

                    data class ClientEventInfo(override val json: JsonObject): PenicillinModel {
                        val component by string  // "urt"
                        val details by model<Details>()  // {...}
                        val element by string  // "user_replied_to_your_tweet"

                        data class Details(override val json: JsonObject): PenicillinModel {
                            val notificationDetails by model<NotificationDetails>()  // {...}

                            data class NotificationDetails(override val json: JsonObject): PenicillinModel {
                                val impressionId by string
                                val metadata by string
                            }
                        }
                    }

                    data class Content(override val json: JsonObject): PenicillinModel {
                        val tweet by model<Tweet>()  // {...}

                        data class Tweet(override val json: JsonObject): PenicillinModel {
                            val displayType by string  // "Tweet"
                            val id by string  // "1036291914469765121"
                        }
                    }
                }
            }
        }
    }

    data class ClearEntriesUnreadState(override val json: JsonObject): PenicillinModel

    data class MarkEntriesUnreadGreaterThanSortIndex(override val json: JsonObject): PenicillinModel {
        val sortIndex by string
    }
}
