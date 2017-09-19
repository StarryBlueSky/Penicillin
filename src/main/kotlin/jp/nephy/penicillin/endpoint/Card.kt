package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.Card
import jp.nephy.penicillin.model.CardState
import jp.nephy.penicillin.response.ResponseObject

class Card(private val client: Client) {
    @POST @UndocumentedAPI
    fun create(cardData: String, vararg options: Pair<String, String?>): ResponseObject<Card> {
        return client.session.new()
                .url("https://caps.twitter.com/v2/cards/create.json")
                .dataAsForm("card_data" to cardData)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getState(cardUri: String, vararg options: Pair<String, String?>): ResponseObject<CardState> {
        return client.session.new()
                .url("https://caps.twitter.com/v2/capi/passthrough/1")
                .param("cards_platform" to "iPhone-13")
                .param("include_cards" to "1")
                .param("twitter:string:card_uri" to cardUri)
                .param("twitter:string:cards_platform" to "iPhone-13")
                .param("twitter:string:response_card_name" to "poll2choice_text_only")
                .params(*options)
                .get()
                .getResponseObject()
    }
}
