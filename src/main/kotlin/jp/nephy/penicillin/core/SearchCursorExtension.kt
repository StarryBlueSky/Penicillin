package jp.nephy.penicillin.core

import jp.nephy.penicillin.models.Search

fun PenicillinJsonObjectResponse<Search>.next(): PenicillinJsonObjectAction<Search> {
    result.searchMetadata.nextResults?.removePrefix("?")?.split("&")?.map { it.split("=", limit = 2) }?.forEach { (k, v) ->
        action.request.builder.parameter(k to v)
    }

    return action.request.jsonObject()
}

fun PenicillinJsonObjectResponse<Search>.hasNext() = !result.searchMetadata.nextResults.isNullOrBlank()
