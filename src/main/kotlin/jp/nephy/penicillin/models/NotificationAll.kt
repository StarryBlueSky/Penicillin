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
import jp.nephy.jsonkt.jsonObjectOrNull
import jp.nephy.jsonkt.parse

data class NotificationAll(override val json: JsonObject): PenicillinModel {
    val globalObjects by model<GlobalObjects>()
    val timeline by model<Timeline>()

    data class GlobalObjects(override val json: JsonObject): PenicillinModel {
        val users by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<User>() } }
        val tweets by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<Status>() } }
        val notifications by lambda { it.jsonObject.values.map { json -> json.jsonObject.parse<Notification>() } }

        data class Notification(override val json: JsonObject): PenicillinModel {
            val icon by model<Icon>()  // {...}
            val id by string
            val message by model<Message>()  // {...}
            val template by model<Template>()  // {...}
            val timestampMs by string  // "1535326933000"

            data class Icon(override val json: JsonObject): JsonModel {
                val id by string  // "retweet_icon"
            }

            data class Message(override val json: JsonObject): JsonModel {
                val entities by modelList<Entities>()  // [...]
                val rtl by boolean  // false
                val text by string

                data class Entities(override val json: JsonObject): JsonModel {
                    val fromIndex by int  // 0
                    val ref by model<Ref>()  // {...}
                    val toIndex by int  // 11

                    data class Ref(override val json: JsonObject): JsonModel {
                        val user by model<User>()  // {...}

                        data class User(override val json: JsonObject): JsonModel {
                            val id by string
                        }
                    }
                }
            }

            data class Template(override val json: JsonObject): JsonModel {
                val aggregateUserActionsV1 by model<AggregateUserActionsV1>()  // {...}

                data class AggregateUserActionsV1(override val json: JsonObject): JsonModel {
                    val fromUsers by modelList<FromUsers>()  // [{"user":{"id":"860076015925600256"}}, ...]
                    val targetObjects by modelList<TargetObject>()  // [{"tweet":{"id":"1031566724192133120"}}]

                    data class FromUsers(override val json: JsonObject): JsonModel {
                        val user by model<User>()  // {...}

                        data class User(override val json: JsonObject): JsonModel {
                            val id by string
                        }
                    }

                    data class TargetObject(override val json: JsonObject): JsonModel {
                        val tweet by model<Tweet>()  // {...}

                        data class Tweet(override val json: JsonObject): JsonModel {
                            val id by string  // "1031566724192133120"
                        }
                    }
                }
            }
        }
    }

    data class Timeline(override val json: JsonObject): PenicillinModel {
        val id by string
        private val instructions by jsonObjectList
        val clearCache = instructions.find { it.contains("clearCache") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.ClearCache>()
        val addEntries = instructions.find { it.contains("addEntries") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.AddEntries>()
        val clearEntriesUnreadState = instructions.find { it.contains("clearEntriesUnreadState") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.ClearEntriesUnreadState>()
        val markEntriesUnreadGreaterThanSortIndex =
            instructions.find { it.contains("markEntriesUnreadGreaterThanSortIndex") }?.values?.firstOrNull()?.jsonObjectOrNull?.parse<NotificationInstruction.MarkEntriesUnreadGreaterThanSortIndex>()
    }
}
