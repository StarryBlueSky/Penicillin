package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byModel
import jp.nephy.jsonkt.byModelList
import jp.nephy.jsonkt.byString

class NotificationInstruction private constructor() {
    data class ClearCache(override val json: JsonObject): PenicillinModel

    data class AddEntries(override val json: JsonObject): PenicillinModel {
        val entities by json.byModelList<Entity>()

        data class Entity(override val json: JsonObject): PenicillinModel {
            val content by json.byModel<Content>()  // {...}
            val entryId by json.byString
            val sortIndex by json.byString

            data class Content(override val json: JsonObject): PenicillinModel {
                val item by json.byModel<Item>()

                data class Item(override val json: JsonObject): PenicillinModel {
                    val clientEventInfo by json.byModel<ClientEventInfo>()  // {...}
                    val content by json.byModel<Content>()  // {...}

                    data class ClientEventInfo(override val json: JsonObject): PenicillinModel {
                        val component by json.byString  // "urt"
                        val details by json.byModel<Details>()  // {...}
                        val element by json.byString  // "user_replied_to_your_tweet"

                        data class Details(override val json: JsonObject): PenicillinModel {
                            val notificationDetails by json.byModel<NotificationDetails>()  // {...}

                            data class NotificationDetails(override val json: JsonObject): PenicillinModel {
                                val impressionId by json.byString
                                val metadata by json.byString
                            }
                        }
                    }

                    data class Content(override val json: JsonObject): PenicillinModel {
                        val tweet by json.byModel<Tweet>()  // {...}

                        data class Tweet(override val json: JsonObject): PenicillinModel {
                            val displayType by json.byString  // "Tweet"
                            val id by json.byString  // "1036291914469765121"
                        }
                    }
                }
            }
        }
    }

    data class ClearEntriesUnreadState(override val json: JsonObject): PenicillinModel

    data class MarkEntriesUnreadGreaterThanSortIndex(override val json: JsonObject): PenicillinModel {
        val sortIndex by json.byString
    }
}
