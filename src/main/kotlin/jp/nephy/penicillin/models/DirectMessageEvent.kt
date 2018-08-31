package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

data class DirectMessageEventList(override val json: JsonObject): PenicillinModel {
    val apps by json.byModel<App>()
    val events by json.byModelList<Event>()
    val nextCursor by json.byNullableString("next_cursor")

    data class App(override val json: JsonObject): PenicillinModel {
        val id by lazy { json.keys.first() }
        val name by json[id].byString
        val url by json[id].byString
    }

    data class Event(override val json: JsonObject): PenicillinModel {
        val createdTimestamp by json.byString("created_timestamp")
        val id by json.byString
        val messageCreate by json.byModel<MessageCreate>(key = "message_create")
        val type by json.byString

        data class MessageCreate(override val json: JsonObject): PenicillinModel {
            val messageData by json.byModel<MessageData>(key = "message_data")
            val senderId by json.byString("sender_id")
            val sourceAppId by json.byNullableString("source_app_id")
            val target by json.byModel<Target>()

            data class MessageData(override val json: JsonObject): PenicillinModel {
                val entities by json.byModel<Entities>()
                val text by json.byString

                data class Entities(override val json: JsonObject): PenicillinModel {
                    val hashtags by json.byModelList<Hashtag>()
                    val symbols by json.byJsonArray
                    val urls by json.byModelList<Url>()
                    val userMentions by json.byJsonArray("user_mentions")

                    data class Hashtag(override val json: JsonObject): PenicillinModel {
                        val indices by json.byIntList
                        val text by json.byString
                    }

                    data class Url(override val json: JsonObject): PenicillinModel {
                        val displayUrl by json.byString("display_url")
                        val expandedUrl by json.byString("expanded_url")
                        val indices by json.byIntList
                        val url by json.byString
                    }
                }
            }

            data class Target(override val json: JsonObject): PenicillinModel {
                val recipientId by json.byString("recipient_id")
            }
        }
    }
}

data class DirectMessageEventShow(override val json: JsonObject): PenicillinModel {
    val event by json.byModel<DirectMessageEventList.Event>()
}
