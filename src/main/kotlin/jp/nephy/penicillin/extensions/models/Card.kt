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

package jp.nephy.penicillin.extensions.models

import jp.nephy.penicillin.core.request.action.JsonObjectApiAction
import jp.nephy.penicillin.endpoints.cards
import jp.nephy.penicillin.endpoints.cards.show
import jp.nephy.penicillin.models.CardState
import jp.nephy.penicillin.models.Status

val Status.hasCard: Boolean
    get() = cardUri != null

fun Status.showCard(name: String): JsonObjectApiAction<CardState> = client.cards.show(cardUri ?: throw IllegalStateException(), name)

val CardState.Card.choices: LinkedHashMap<String, Int>
    get() = linkedMapOf<String, Int>().also { 
        it.putIfNotNull(bindingValues.choice1Label?.value, bindingValues.choice1Count?.value?.toIntOrNull())
        it.putIfNotNull(bindingValues.choice2Label?.value, bindingValues.choice2Count?.value?.toIntOrNull())
        it.putIfNotNull(bindingValues.choice3Label?.value, bindingValues.choice3Count?.value?.toIntOrNull())
        it.putIfNotNull(bindingValues.choice4Label?.value, bindingValues.choice4Count?.value?.toIntOrNull())
    }

val CardState.Card.isFinalResult: Boolean
    get() = bindingValues.countsAreFinal?.value == true

val CardState.Card.endAt: String?
    get() = bindingValues.endDatetimeUtc?.value

val CardState.Card.lastUpdateAt: String?
    get() = bindingValues.lastUpdatedDatetimeUtc?.value

val CardState.Card.minutes: Int?
    get() = bindingValues.durationMinutes?.value?.toIntOrNull()

private fun <K, V> MutableMap<K, V>.putIfNotNull(key: K?, value: V?) {
    if (key != null && value != null) {
        put(key, value)
    }
}
