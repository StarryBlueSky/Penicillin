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

@file:Suppress("UNUSED")

package blue.starry.penicillin.endpoints.cards

import blue.starry.penicillin.core.emulation.EmulationMode
import blue.starry.penicillin.core.request.EndpointHost
import blue.starry.penicillin.core.request.action.JsonObjectApiAction
import blue.starry.penicillin.core.request.formBody
import blue.starry.penicillin.core.session.post
import blue.starry.penicillin.endpoints.Cards
import blue.starry.penicillin.endpoints.Option
import blue.starry.penicillin.models.Card

/**
 * Creates new "card" object. This is what we call polls.
 *
 * @param cardData Required. The card data encoded with JSON.
 * @param options Optional. Custom parameters of this request.
 * @receiver [Cards] endpoint instance.
 * @return [JsonObjectApiAction] for [Card] model.
 */
public fun Cards.create(
    cardData: String,
    vararg options: Option
): JsonObjectApiAction<Card> = client.session.post("/v2/cards/create.json", EndpointHost.Card) {
    emulationModes += EmulationMode.TwitterForiPhone

    formBody(
        "card_data" to cardData,
        *options
    )
}.jsonObject { Card(it, client) }
