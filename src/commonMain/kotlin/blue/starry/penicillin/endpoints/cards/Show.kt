/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package blue.starry.penicillin.endpoints.cards

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.parameters
import blue.starry.penicillin.core.session.get
import blue.starry.penicillin.endpoints.Cards
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.CardState

/**
 * Returns card data.
 *
 * @param cardUri Required. The card uri. Usually starts with "card-uri://".
 * @param options Optional. Custom parameters of this request.
 * @receiver [Cards] endpoint instance.
 * @return [JsonObjectApiAction] for [CardState] model.
 */
fun Cards.show(
    cardUri: String,
    responseCardName: String = "poll4choice_text_only",
    vararg options: Option
) = client.session.get("/v2/capi/passthrough/1", EndpointHost.Card) {
    emulationModes += EmulationMode.TwitterForiPhone

    parameters(
        "cards_platform" to "iPhone-13",
        "include_cards" to "1",
        "twitter:string:card_uri" to cardUri,
        "twitter:string:cards_platform" to "iPhone-13",
        "twitter:string:response_card_name" to responseCardName,
        *options
    )
}.jsonObject { CardState(it, client) }
