package jp.nephy.penicillin.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import jp.nephy.penicillin.api.endpoint.*
import jp.nephy.penicillin.api.model.TweetModel
import jp.nephy.penicillin.request.HTTPMethod
import jp.nephy.penicillin.request.handler.OAuthRequestHandler
import java.net.URL

class Client(private val oauth: OAuthRequestHandler) {
    fun getAccountSettings(data: Map<String,String>?=null) = AccountSettings(oauth).getAsObject<AccountSettingsModel>(data)
    fun getAccountCredentials(data: Map<String,String>?=null) = AccountVerifyCredentials(oauth).getAsObject<AccountVerifyCredentialsModel>(data)
    fun getApplicationRateLimitStatus(data: Map<String,String>?=null) = ApplicationRateLimitStatus(oauth).getAsObject<ApplicationRateLimitStatusModel>(data)
    fun getBlocksIds(data: Map<String, String>?=null) = BlocksIds(oauth).getAsObject<BlocksIdsModel>(data)
    fun getBlocksList(data: Map<String, String>?=null) = BlocksList(oauth).getAsObject<BlocksListModel>(data)
    fun getFavoritesList(data: Map<String, String>?=null) = FavoritesList(oauth).getAsList<TweetModel>(data)

    fun getHelpConfiguration(data: Map<String, String>?=null) = HelpConfiguration(oauth).getAsObject<HelpConfigurationModel>(data)
    fun getHelpLanguages(data: Map<String, String>?=null) = HelpLanguages(oauth).getAsList<HelpLanguagesModel>(data)
    fun getHelpPrivacy(data: Map<String, String>?=null) = HelpPrivacy(oauth).getAsObject<HelpPrivacyModel>(data)
    fun getHelpTos(data: Map<String, String>?=null) = HelpTos(oauth).getAsObject<HelpTosModel>(data)

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
