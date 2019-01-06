@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.models.SavedSearch

class SavedSearches(override val client: PenicillinClient): Endpoint {
    fun list(vararg options: Pair<String, Any?>) = client.session.get("/1.1/saved_searches/list.json") {
        parameter(*options)
    }.jsonArray<SavedSearch>()

    fun show(id: Long, vararg options: Pair<String, Any?>) = client.session.get("/1.1/saved_searches/show/$id.json") {
        parameter(*options)
    }.jsonObject<SavedSearch>()

    fun create(query: String, vararg options: Pair<String, Any?>) = client.session.post("/1.1/saved_searches/create.json") {
        body {
            form {
                add("query" to query, *options)
            }
        }
    }.jsonObject<SavedSearch>()

    fun destroy(id: Long, vararg options: Pair<String, Any?>) = client.session.post("/1.1/saved_searches/destroy/$id.json") {
        body {
            form {
                add(*options)
            }
        }
    }.jsonObject<SavedSearch>()
}
