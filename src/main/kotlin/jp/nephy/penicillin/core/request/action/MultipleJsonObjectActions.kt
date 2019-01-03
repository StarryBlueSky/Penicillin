package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.core.exceptions.PenicillinException
import jp.nephy.penicillin.core.request.ApiRequest
import jp.nephy.penicillin.core.response.JsonObjectResponse
import jp.nephy.penicillin.models.PenicillinModel
import kotlinx.coroutines.CancellationException
import kotlin.reflect.KClass

private typealias JsonObjectActionCallback<M> = suspend (results: PenicillinMultipleJsonObjectActions.Results<M>) -> JsonObjectApiAction<*>

// TODO
data class PenicillinMultipleJsonObjectActions<M: PenicillinModel>(val first: JsonObjectApiAction<M>, private val requests: List<JsonObjectActionCallback<M>>): ApiAction<List<JsonObjectResponse<*>>> {
    override val request: ApiRequest
        get() = first.request

    class Builder<M: PenicillinModel>(private val first: () -> JsonObjectApiAction<M>) {
        private val requests = mutableListOf<JsonObjectActionCallback<M>>()
        fun request(callback: JsonObjectActionCallback<M>) = apply {
            requests.add(callback)
        }

        internal fun build(): PenicillinMultipleJsonObjectActions<M> {
            return PenicillinMultipleJsonObjectActions(first(), requests)
        }
    }

    class Results<M: PenicillinModel>(val first: JsonObjectResponse<M>, val responses: Map<KClass<out PenicillinModel>, List<JsonObjectResponse<*>>>) {
        inline fun <reified T: PenicillinModel> responses(): List<JsonObjectResponse<T>> {
            @Suppress("UNCHECKED_CAST") return responses[T::class].orEmpty().map { it as JsonObjectResponse<T> }
        }
    }

    @Throws(PenicillinException::class, CancellationException::class)
    override suspend fun await(): List<JsonObjectResponse<*>> {
        val first = first.await()
        val responses = mutableMapOf<KClass<out PenicillinModel>, MutableList<JsonObjectResponse<*>>>()
        val responsesList = mutableListOf<JsonObjectResponse<*>>()
        for (request in requests) {
            val result = runCatching {
                request.invoke(Results(first, responses)).await()
            }.getOrNull() ?: continue

            if (result.model in responses) {
                responses[result.model]!!.add(result)
            } else {
                responses[result.model] = mutableListOf(result)
            }
            responsesList += result
        }

        return responsesList
    }

    operator fun plus(callback: JsonObjectActionCallback<M>): PenicillinMultipleJsonObjectActions<M> {
        return Builder {
            first
        }.also { builder ->
            requests.forEach {
                builder.request(it)
            }
        }.request(callback).build()
    }
}
