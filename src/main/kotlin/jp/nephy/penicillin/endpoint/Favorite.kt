package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.misc.StatusID
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class Favorite(private val client: Client) {
    @GET
    fun getList(userId: Long?=null, screenName: String?=null, sinceId: StatusID?=null, maxId: StatusID?=null, count: Int?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<Status> {
        return client.session.new()
                .url("/favorites/list.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("include_entities" to includeEntities)
                .params(*options)
                .get()
                .getResponseList()
    }

    @POST
    fun create(id: StatusID, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/favorites/create.json")
                .dataAsForm("id" to id)
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(id: StatusID, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<Status> {
        return client.session.new()
                .url("/favorites/destroy.json")
                .dataAsForm("id" to id)
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}
