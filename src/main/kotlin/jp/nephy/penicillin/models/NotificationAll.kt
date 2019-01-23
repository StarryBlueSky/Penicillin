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
import jp.nephy.jsonkt.delegation.*
import jp.nephy.jsonkt.jsonObjectOrNull
import jp.nephy.penicillin.PenicillinClient

data class NotificationAll(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
    val globalObjects by penicillinModel<GlobalObjects>()
    val timeline by penicillinModel<Timeline>()

    data class GlobalObjects(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        val users by lambda { it.jsonObject.values.map { json -> client.parsePenicillinModel<User>(json) } }
        val tweets by lambda { it.jsonObject.values.map { json -> client.parsePenicillinModel<Status>(json) } }
        val notifications by lambda { it.jsonObject.values.map { json -> client.parsePenicillinModel<Notification>(json) } }

        data class Notification(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
            val icon by penicillinModel<Icon>()  // {...}
            val id by string
            val message by penicillinModel<Message>()  // {...}
            val template by penicillinModel<Template>()  // {...}
            val timestampMs by string  // "1535326933000"

            data class Icon(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                val id by string  // "retweet_icon"
            }

            data class Message(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                val entities by penicillinModelList<Entities>()  // [...]
                val rtl by boolean  // false
                val text by string

                data class Entities(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                    val fromIndex by int  // 0
                    val ref by penicillinModel<Ref>()  // {...}
                    val toIndex by int  // 11

                    data class Ref(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                        val user by penicillinModel<User>()  // {...}

                        data class User(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                            val id by string
                        }
                    }
                }
            }

            data class Template(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                val aggregateUserActionsV1 by penicillinModel<AggregateUserActionsV1>()  // {...}

                data class AggregateUserActionsV1(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                    val fromUsers by penicillinModelList<FromUsers>()  // [{"user":{"id":"860076015925600256"}}, ...]
                    val targetObjects by penicillinModelList<TargetObject>()  // [{"tweet":{"id":"1031566724192133120"}}]

                    data class FromUsers(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                        val user by penicillinModel<User>()  // {...}

                        data class User(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                            val id by string
                        }
                    }

                    data class TargetObject(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                        val tweet by penicillinModel<Tweet>()  // {...}

                        data class Tweet(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
                            val id by string  // "1031566724192133120"
                        }
                    }
                }
            }
        }
    }

    data class Timeline(override val json: JsonObject, override val client: PenicillinClient): PenicillinModel {
        val id by string
        private val instructions by jsonObjectList
        val clearCache = instructions.find { it.contains("clearCache") }?.values?.firstOrNull()?.jsonObjectOrNull?.let {
            client.parsePenicillinModelOrNull<NotificationInstruction.ClearCache>(it)
        }
        val addEntries = instructions.find { it.contains("addEntries") }?.values?.firstOrNull()?.jsonObjectOrNull?.let { 
            client.parsePenicillinModelOrNull<NotificationInstruction.AddEntries>(it)
        }
        val clearEntriesUnreadState = instructions.find { it.contains("clearEntriesUnreadState") }?.values?.firstOrNull()?.jsonObjectOrNull?.let {
            client.parsePenicillinModelOrNull<NotificationInstruction.ClearEntriesUnreadState>(it)
        }
        val markEntriesUnreadGreaterThanSortIndex =
            instructions.find { it.contains("markEntriesUnreadGreaterThanSortIndex") }?.values?.firstOrNull()?.jsonObjectOrNull?.let {
                client.parsePenicillinModelOrNull<NotificationInstruction.MarkEntriesUnreadGreaterThanSortIndex>(it)
            }
    }
}
