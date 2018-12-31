package jp.nephy.penicillin.core

import jp.nephy.penicillin.models.Search

fun PenicillinJsonObjectResponse<Search>.next(): PenicillinJsonObjectAction<Search> {
    for ((k, v) in result.searchMetadata.nextResults.removePrefix("?").split("&").map { it.split("=", limit = 2) }) {
        action.request.builder.parameter(k to v)
    }

    return action.request.jsonObject()
}
