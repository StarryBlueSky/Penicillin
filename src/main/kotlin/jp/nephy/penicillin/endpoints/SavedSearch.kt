package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.SavedSearch


class SavedSearch(override val client: PenicillinClient): Endpoint {
    fun list(vararg options: Pair<String, Any?>)= client.session.getList<SavedSearch>("/saved_searches/list.json") {
        query(*options)
    }

    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<SavedSearch>("/saved_searches/show/$id.json") {
        query(*options)
    }

    fun create(query: String, vararg options: Pair<String, Any?>)= client.session.postObject<SavedSearch>("/saved_searches/create.json") {
        form("query" to query, *options)
    }

    fun destroy(id: Long, vararg options: Pair<String, Any?>)= client.session.postObject<SavedSearch>("/saved_searches/destroy/$id.json") {
        form(*options)
    }
}
