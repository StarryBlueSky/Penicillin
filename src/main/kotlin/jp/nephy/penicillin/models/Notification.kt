package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.*

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
