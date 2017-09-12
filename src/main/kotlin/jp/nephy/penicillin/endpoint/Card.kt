package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.Card
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
}
