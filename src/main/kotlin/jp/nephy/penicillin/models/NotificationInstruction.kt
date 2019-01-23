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
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string
import jp.nephy.penicillin.PenicillinClient

object NotificationInstruction {
    data class ClearCache(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel

    data class AddEntries(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        val entities by penicillinModelList<Entity>()

        data class Entity(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
            val content by penicillinModel<Content>()  // {...}
            val entryId by string
            val sortIndex by string

            data class Content(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                val item by penicillinModel<Item>()

                data class Item(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                    val clientEventInfo by penicillinModel<ClientEventInfo>()  // {...}
                    val content by penicillinModel<Content>()  // {...}

                    data class ClientEventInfo(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                        val component by string  // "urt"
                        val details by penicillinModel<Details>()  // {...}
                        val element by string  // "user_replied_to_your_tweet"

                        data class Details(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                            val notificationDetails by penicillinModel<NotificationDetails>()  // {...}

                            data class NotificationDetails(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                                val impressionId by string
                                val metadata by string
                            }
                        }
                    }

                    data class Content(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                        val tweet by penicillinModel<Tweet>()  // {...}

                        data class Tweet(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                            val displayType by string  // "Tweet"
                            val id by string  // "1036291914469765121"
                        }
                    }
                }
            }
        }
    }

    data class ClearEntriesUnreadState(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel

    data class MarkEntriesUnreadGreaterThanSortIndex(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        val sortIndex by string
    }
}
