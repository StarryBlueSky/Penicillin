@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.Card
import jp.nephy.penicillin.models.CardState

class Cards(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun create(cardData: String, vararg options: Pair<String, Any?>) = client.session.post("/v2/cards/create.json", EndpointHost.Card) {
        body {
            form {
                add("card_data" to cardData, *options)
            }
        }
    }.jsonObject<Card>()

    @PrivateEndpoint
    fun show(cardUri: String, vararg options: Pair<String, Any?>) = client.session.get("/v2/capi/passthrough/1", EndpointHost.Card) {
        parameter(
            "cards_platform" to "iPhone-13",
            "include_cards" to "1",
            "twitter:string:card_uri" to cardUri,
            "twitter:string:cards_platform" to "iPhone-13",
            "twitter:string:response_card_name" to "poll4choice_text_only",
            *options
        )
    }.jsonObject<CardState>()
}
