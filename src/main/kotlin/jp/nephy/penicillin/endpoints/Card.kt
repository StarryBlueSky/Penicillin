package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Card
import jp.nephy.penicillin.models.CardState


class Card(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun create(cardData: String, vararg options: Pair<String, Any?>)= client.session.postObject<Card>("https://caps.twitter.com/v2/cards/create.json") {
        form("card_data" to cardData, *options)
    }

    @PrivateEndpoint
    fun show(cardUri: String, vararg options: Pair<String, Any?>)= client.session.getObject<CardState>("https://caps.twitter.com/v2/capi/passthrough/1") {
        query("cards_platform" to "iPhone-13", "include_cards" to "1", "twitter:string:card_uri" to cardUri, "twitter:string:cards_platform" to "iPhone-13", "twitter:string:response_card_name" to "poll4choice_text_only", *options)
    }
}
