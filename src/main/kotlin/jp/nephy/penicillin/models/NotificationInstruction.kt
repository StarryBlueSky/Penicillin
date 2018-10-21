@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*

class NotificationInstruction private constructor() {
    data class ClearCache(override val json: ImmutableJsonObject): PenicillinModel

    data class AddEntries(override val json: ImmutableJsonObject): PenicillinModel {
        val entities by modelList<Entity>()

        data class Entity(override val json: ImmutableJsonObject): PenicillinModel {
            val content by model<Content>()  // {...}
            val entryId by string
            val sortIndex by string

            data class Content(override val json: ImmutableJsonObject): PenicillinModel {
                val item by model<Item>()

                data class Item(override val json: ImmutableJsonObject): PenicillinModel {
                    val clientEventInfo by model<ClientEventInfo>()  // {...}
                    val content by model<Content>()  // {...}

                    data class ClientEventInfo(override val json: ImmutableJsonObject): PenicillinModel {
                        val component by string  // "urt"
                        val details by model<Details>()  // {...}
                        val element by string  // "user_replied_to_your_tweet"

                        data class Details(override val json: ImmutableJsonObject): PenicillinModel {
                            val notificationDetails by model<NotificationDetails>()  // {...}

                            data class NotificationDetails(override val json: ImmutableJsonObject): PenicillinModel {
                                val impressionId by string
                                val metadata by string
                            }
                        }
                    }

                    data class Content(override val json: ImmutableJsonObject): PenicillinModel {
                        val tweet by model<Tweet>()  // {...}

                        data class Tweet(override val json: ImmutableJsonObject): PenicillinModel {
                            val displayType by string  // "Tweet"
                            val id by string  // "1036291914469765121"
                        }
                    }
                }
            }
        }
    }

    data class ClearEntriesUnreadState(override val json: ImmutableJsonObject): PenicillinModel

    data class MarkEntriesUnreadGreaterThanSortIndex(override val json: ImmutableJsonObject): PenicillinModel {
        val sortIndex by string
    }
}
