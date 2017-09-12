package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class SavedSearch(private val client: Client) {
    @GET
    fun getList(vararg options: Pair<String, String?>): ResponseList<SavedSearch> {
        return client.session.new()
                .url("/saved_searches/list.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getSearch(id: Long, vararg options: Pair<String, String?>): ResponseObject<SavedSearch> {
        return client.session.new()
                .url("/saved_searches/show/$id.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun create(query: String, vararg options: Pair<String, String?>): ResponseObject<SavedSearch> {
        return client.session.new()
                .url("/saved_searches/create.json")
                .dataAsForm("query" to query)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(id: Long, vararg options: Pair<String, String?>): ResponseObject<SavedSearch> {
        return client.session.new()
                .url("/saved_searches/destroy/$id.json")
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}