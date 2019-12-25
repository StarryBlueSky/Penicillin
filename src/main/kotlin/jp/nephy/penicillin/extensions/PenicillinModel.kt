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

@file:Suppress("UNUSED", "PublicApiImplicitType")

package jp.nephy.penicillin.extensions

import jp.nephy.jsonkt.*
import jp.nephy.jsonkt.delegation.*
import jp.nephy.penicillin.core.experimental.PenicillinExperimentalApi
import jp.nephy.penicillin.core.session.ApiClient
import jp.nephy.penicillin.core.session.Session
import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

@PublishedApi
internal object NoopApiClient: ApiClient {
    override val session: Session
        get() = throw NotImplementedError()

    override fun close() {
        throw UnsupportedOperationException()
    }
}

/*
    parseModel
 */

/**
 * Parses this [JsonElement] with [model] bind to [client].
 */
fun <M: PenicillinModel> JsonElement.parseModel(model: KClass<M>, client: ApiClient): M {
    return parseAs(model, client)
}

/**
 * Parses this [JsonElement] with [model].
 */
@PenicillinExperimentalApi
fun <M: PenicillinModel> JsonElement.parseModel(model: KClass<M>): M {
    return parseModel(model, NoopApiClient)
}

/**
 * Parses this [JsonElement] with [M] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonElement.parseModel(client: ApiClient): M {
    return parse(client)
}

/**
 * Parses this [JsonElement] with [M].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonElement.parseModel(): M {
    return parseModel(NoopApiClient)
}

/*
    parseModelOrNull
 */

/**
 * Parses this [JsonElement] with [model] bind to [client], or null.
 */
fun <M: PenicillinModel> JsonElement.parseModelOrNull(model: KClass<M>, client: ApiClient): M? {
    return parseAsOrNull(model, client)
}

/**
 * Parses this [JsonElement] with [model], or null.
 */
@PenicillinExperimentalApi
fun <M: PenicillinModel> JsonElement.parseModelOrNull(model: KClass<M>): M? {
    return parseModelOrNull(model, NoopApiClient)
}

/**
 * Parses this [JsonElement] with [M] bind to [client], or null.
 */
inline fun <reified M: PenicillinModel> JsonElement.parseModelOrNull(client: ApiClient): M? {
    return parseOrNull(client)
}

/**
 * Parses this [JsonElement] with [M], or null.
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonElement.parseModelOrNull(): M? {
    return parseModelOrNull(NoopApiClient)
}

/*
    parseModelList
 */

/**
 * Parses this [JsonElement] with [model] bind to [client].
 */
fun <M: PenicillinModel> JsonElement.parseModelList(model: KClass<M>, client: ApiClient): List<M> {
    return parseListAs(model, client)
}

/**
 * Parses this [JsonElement] with [model].
 */
@PenicillinExperimentalApi
fun <M: PenicillinModel> JsonElement.parseModelList(model: KClass<M>): List<M> {
    return parseModelList(model, NoopApiClient)
}

/**
 * Parses this [JsonElement] with [M] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonElement.parseModelList(client: ApiClient): List<M> {
    return parseList(client)
}

/**
 * Parses this [JsonElement] with [M].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonElement.parseModelList(): List<M> {
    return parseModelList(NoopApiClient)
}

/*
    parseModelListOrNull
 */

/**
 * Parses this [JsonElement] with [model] bind to [client], or null.
 */
fun <M: PenicillinModel> JsonElement.parseModelListOrNull(model: KClass<M>, client: ApiClient): List<M>? {
    return parseListAsOrNull(model, client)
}

/**
 * Parses this [JsonElement] with [model], or null.
 */
@PenicillinExperimentalApi
fun <M: PenicillinModel> JsonElement.parseModelListOrNull(model: KClass<M>): List<M>? {
    return parseModelListOrNull(model, NoopApiClient)
}

/**
 * Parses this [JsonElement] with [M] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonElement.parseModelListOrNull(client: ApiClient): List<M>? {
    return parseListOrNull(client)
}

/**
 * Parses this [JsonElement] with [M].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonElement.parseModelListOrNull(): List<M>? {
    return parseModelListOrNull(NoopApiClient)
}

/*
    Model delegations
 */

/**
 * Delegates model parsing by [key].
 */
inline fun <reified M: PenicillinModel> PenicillinModel.penicillinModel(key: String? = null) = model<M>(key, client)

/**
 * Delegates nullable model parsing by [key].
 */
inline fun <reified M: PenicillinModel> PenicillinModel.nullablePenicillinModel(key: String? = null) = nullableModel<M>(key, client)

/**
 * Delegates model list parsing by [key].
 */
inline fun <reified M: PenicillinModel> PenicillinModel.penicillinModelList(key: String? = null) = modelList<M>(key, client)

/**
 * Delegates nullable model list parsing by [key].
 */
inline fun <reified M: PenicillinModel> PenicillinModel.nullablePenicillinModelList(key: String? = null) = nullableModelList<M>(key, client)

/**
 * Delegates model parsing by [key] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonObject.byPenicillinModel(client: ApiClient, key: String? = null) = byModel<M>(key, client)

/**
 * Delegates model parsing by [key].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonObject.byPenicillinModel(key: String? = null) = byPenicillinModel<M>(NoopApiClient, key)

/**
 * Delegates nullable model parsing by [key] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonObject.byNullablePenicillinModel(client: ApiClient, key: String? = null) = byNullableModel<M>(key, client)

/**
 * Delegates nullable model parsing by [key].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonObject.byNullablePenicillinModel(key: String? = null) = byNullablePenicillinModel<M>(NoopApiClient, key)

/**
 * Delegates model list parsing by [key] bind to [client].
 */
inline fun <reified M: PenicillinModel> JsonObject.byPenicillinModelList(client: ApiClient, key: String? = null) = byModelList<M>(key, client)

/**
 * Delegates model list parsing by [key].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonObject.byPenicillinModelList(key: String? = null) = byPenicillinModelList<M>(NoopApiClient, key)

/**
 * Delegates nullable model list parsing by [key] bind to [client].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonObject.byNullablePenicillinModelList(client: ApiClient, key: String? = null) = byNullableModelList<M>(key, client)

/**
 * Delegates nullable model list parsing by [key].
 */
@PenicillinExperimentalApi
inline fun <reified M: PenicillinModel> JsonObject.byNullablePenicillinModelList(key: String? = null) = byNullablePenicillinModelList<M>(NoopApiClient, key)
