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

@file:Suppress("PublicApiImplicitType")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonElement
import jp.nephy.jsonkt.delegation.JsonModel
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.parse
import jp.nephy.jsonkt.parseListOrNull
import jp.nephy.jsonkt.parseOrNull
import jp.nephy.penicillin.PenicillinClient
import kotlin.reflect.KClass

interface PenicillinModel: JsonModel {
    val client: PenicillinClient
}

internal fun <M: PenicillinModel> PenicillinClient.parsePenicillinModelOrNull(model: KClass<M>, json: JsonElement): M? {
    return json.parseOrNull(model, this)
}

internal fun <M: PenicillinModel> PenicillinClient.parsePenicillinModelListOrNull(model: KClass<M>, json: JsonElement): List<M>? {
    return json.parseListOrNull(model, this)
}

internal inline fun <reified M: PenicillinModel> PenicillinClient.parsePenicillinModel(json: JsonElement): M {
    return json.parse(this)
}

internal inline fun <reified M: PenicillinModel> PenicillinClient.parsePenicillinModelOrNull(json: JsonElement): M? {
    return json.parseOrNull(this)
}

inline fun <reified M: PenicillinModel> PenicillinModel.penicillinModel(key: String? = null) = model<M>(key, client)

inline fun <reified M: PenicillinModel> PenicillinModel.penicillinModelList(key: String? = null) = modelList<M>(key, client)
