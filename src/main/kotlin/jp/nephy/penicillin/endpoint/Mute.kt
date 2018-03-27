package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.CursorUsers
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.response.ResponseCursorObject
import jp.nephy.penicillin.response.ResponseObject


class Mute(private val client: Client) {
    @GET
    @Cursorable
    fun getIds(stringifyIds: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorIds> {
        return client.session.new()
                .url("/mutes/users/ids.json")
                .param("stringify_ids" to stringifyIds)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET
    @Cursorable
    fun getList(includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorUsers> {
        return client.session.new()
                .url("/mutes/users/list.json")
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @POST
    fun create(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/mutes/users/create.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(screenName: String?=null, userId: Long?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/mutes/users/destroy.json")
                .dataAsForm("screen_name" to screenName)
                .dataAsForm("user_id" to userId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }
}
