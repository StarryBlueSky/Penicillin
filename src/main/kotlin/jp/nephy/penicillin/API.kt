package jp.nephy.penicillin

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URL

class API {
    companion object {
        fun authenticate(ck: ConsumerKey, cs :ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): API {
            return API().authenticate(ck, cs, at, ats, uuid, deviceId)
        }
    }

    private var request: OAuthRequest? = null

    fun authenticate(ck: ConsumerKey, cs :ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): API {
        request = OAuthRequest(ck, cs, at, ats, uuid, deviceId)
        return this
    }

    fun createPollTweet(status: String, choices: List<String>, minutes: Int=1440): JsonObject? {
        val CARDS_CREATE_URL = "https://caps.twitter.com/v2/cards/create.json"
        val UPDATE_STATUS_URL = "https://api.twitter.com/1.1/statuses/update.json"

        if (status.length > 140) {
            throw IllegalArgumentException("status must have less than 140 charactors.")
        }
        if (choices.size < 2 || choices.size > 5) {
            throw IllegalArgumentException("choices must have 2, 3 or 4 Strings.")
        }
        if (minutes < 0 || minutes > 10080) {
            throw IllegalArgumentException("minutes must be in range 1..10080.")
        }

        val paramPoll = mutableMapOf<String,String>().apply {
            put("card_data", Gson().toJson(linkedMapOf<String,Any>().apply {
                choices.forEachIndexed { i, choice ->
                    put("twitter:string:choice${i + 1}_label", choice)
                }
                put("twitter:api:api:endpoint", "1")
                put("twitter:card", "poll4choice_text_only")
                put("twitter:long:duration_minutes", minutes)
            }))
        }

        var result: JsonObject? = null
        request?.apply {
            val poll = send(HTTPMethod.POST, URL(CARDS_CREATE_URL), paramPoll)

            val paramTweet = mutableMapOf<String,String>().apply {
                put("status", status)
                put("card_uri", poll.get("card_uri").asString)
            }
            result = send(HTTPMethod.POST, URL(UPDATE_STATUS_URL), paramTweet)
        }

        return result
    }
}
