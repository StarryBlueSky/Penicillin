package jp.nephy.penicillin

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.api.endpoint.*
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.credential.*
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

class API {
    companion object {
        fun authenticate(ck: ConsumerKey, cs : ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): API {
            return API().authenticate(ck, cs, at, ats, uuid, deviceId)
        }
    }

    lateinit var oauth: OAuthRequestHandler

    fun authenticate(ck: ConsumerKey, cs : ConsumerSecret, at: AccessToken, ats: AccessTokenSecret, uuid: String?=null, deviceId: String?=null): API {
        oauth = OAuthRequestHandler(ck, cs, at, ats, uuid, deviceId)
        return this
    }

    fun getAccountSettings(data: Map<String,String>?=null) = AccountSettings(oauth).getAsObject<AccountSettingsModel>(data)
    fun getAccountCredentials(data: Map<String,String>?=null) = AccountVerifyCredentials(oauth).getAsObject<AccountVerifyCredentialsModel>(data)
    fun getApplicationRateLimitStatus(data: Map<String,String>?=null) = ApplicationRateLimitStatus(oauth).getAsObject<ApplicationRateLimitStatusModel>(data)
    fun getBlocksIds(data: Map<String, String>?=null) = BlocksIds(oauth).getAsObject<BlocksIdsModel>(data)
    fun getBlocksList(data: Map<String, String>?=null) = BlocksList(oauth).getAsObject<BlocksListModel>(data)

    fun createPollTweet(status: String, choices: List<String>, minutes: Int=1440) {
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

        oauth.apply {
            val poll = send(HTTPMethod.POST, URL(CARDS_CREATE_URL), paramPoll)

            val paramTweet = mutableMapOf<String,String>().apply {
                put("status", status)
                put("card_uri", Gson().fromJson(poll.third.component1(), JsonObject::class.java).get("card_uri").asString)
            }
            send(HTTPMethod.POST, URL(UPDATE_STATUS_URL), paramTweet)
        }
    }
}
