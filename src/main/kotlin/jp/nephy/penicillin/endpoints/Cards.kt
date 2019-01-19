/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2019 Nephy Project Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.EndpointHost
import jp.nephy.penicillin.core.session.get
import jp.nephy.penicillin.core.session.post
import jp.nephy.penicillin.models.Card
import jp.nephy.penicillin.models.CardState

val PenicillinClient.cards: Cards
    get() = Cards(this)

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
