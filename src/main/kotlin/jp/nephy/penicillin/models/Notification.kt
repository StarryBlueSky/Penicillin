package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*

data class Notification(override val json: JsonObject): PenicillinModel {
    val icon by json.byModel<Icon>()  // {...}
    val id by json.byString
    val message by json.byModel<Message>()  // {...}
    val template by json.byModel<Template>()  // {...}
    val timestampMs by json.byString  // "1535326933000"

    data class Icon(override val json: JsonObject): JsonModel {
        val id by json.byString  // "retweet_icon"
    }

    data class Message(override val json: JsonObject): JsonModel {
        val entities by json.byModelList<Entities>()  // [...]
        val rtl by json.byBool  // false
        val text by json.byString

        data class Entities(override val json: JsonObject): JsonModel {
            val fromIndex by json.byInt  // 0
            val ref by json.byModel<Ref>()  // {...}
            val toIndex by json.byInt  // 11

            data class Ref(override val json: JsonObject): JsonModel {
                val user by json.byModel<User>()  // {...}

                data class User(override val json: JsonObject): JsonModel {
                    val id by json.byString
                }
            }
        }
    }

    data class Template(override val json: JsonObject): JsonModel {
        val aggregateUserActionsV1 by json.byModel<AggregateUserActionsV1>()  // {...}

        data class AggregateUserActionsV1(override val json: JsonObject): JsonModel {
            val fromUsers by json.byModelList<FromUsers>()  // [{"user":{"id":"860076015925600256"}}, ...]
            val targetObjects by json.byModelList<TargetObject>()  // [{"tweet":{"id":"1031566724192133120"}}]

            data class FromUsers(override val json: JsonObject): JsonModel {
                val user by json.byModel<User>()  // {...}

                data class User(override val json: JsonObject): JsonModel {
                    val id by json.byString
                }
            }

            data class TargetObject(override val json: JsonObject): JsonModel {
                val tweet by json.byModel<Tweet>()  // {...}

                data class Tweet(override val json: JsonObject): JsonModel {
                    val id by json.byString  // "1031566724192133120"
                }
            }
        }
    }
}
