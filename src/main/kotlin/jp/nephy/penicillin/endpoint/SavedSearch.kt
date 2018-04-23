package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.model.SavedSearch


class SavedSearch(override val client: PenicillinClient): Endpoint {
    fun list(vararg options: Pair<String, Any?>)= client.session.getList<SavedSearch>("/saved_searches/list.json") {
        query(*options)
    }

    fun show(id: Long, vararg options: Pair<String, Any?>)= client.session.getObject<SavedSearch>("/saved_searches/show/$id.json") {
        query(*options)
    }

    fun create(query: String, vararg options: Pair<String, Any?>)= client.session.postObject<SavedSearch>("/saved_searches/create.json") {
        query("query" to query, *options)
    }

    fun destroy(id: Long, vararg options: Pair<String, Any?>)= client.session.postObject<SavedSearch>("/saved_searches/destroy/$id.json") {
        query(*options)
    }
}
