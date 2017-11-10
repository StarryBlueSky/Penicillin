package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.model.CursorIds
import jp.nephy.penicillin.model.CursorUsers
import jp.nephy.penicillin.response.ResponseCursorObject

@Suppress("UNUSED")
class Follower(private val client: Client) {
    @GET @Cursorable
    fun getIds(userId: Long?=null, screenName: String?=null, stringifyIds: Boolean?=null, count: Int?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorIds> {
        return client.session.new()
                .url("/followers/ids.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("stringify_ids" to stringifyIds)
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }

    @GET @Cursorable
    fun getList(userId: Long?=null, screenName: String?=null, count: Int?=null, skipStatus: Boolean?=null, includeUserEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseCursorObject<CursorUsers> {
        return client.session.new()
                .url("/followers/list.json")
                .param("user_id" to userId)
                .param("screen_name" to screenName)
                .param("count" to count)
                .param("skip_status" to skipStatus)
                .param("include_user_entities" to includeUserEntities)
                .params(*options)
                .get()
                .getResponseCursorObject()
    }
}