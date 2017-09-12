package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.Cursorable
import jp.nephy.penicillin.annotation.DELETE
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.model.*
import jp.nephy.penicillin.model.DirectMessage
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class DirectMessage(private val client: Client) {
    @GET
    fun getMessage(id: Long, vararg options: Pair<String, String?>): ResponseObject<DirectMessage> {
        return client.session.new()
                .url("/direct_messages/show.json")
                .param("id" to id)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getMessages(sinceId: Long?=null, maxId: Long?=null, count: Int?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<DirectMessage> {
        return client.session.new()
                .url("/direct_messages.json")
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getSentMessages(sinceId: Long?=null, maxId: Long?=null, count: Int?=null, page: Int?=null, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseList<DirectMessage> {
        return client.session.new()
                .url("/direct_messages/sent.json")
                .param("since_id" to sinceId)
                .param("max_id" to maxId)
                .param("count" to count)
                .param("include_entities" to includeEntities)
                .param("page" to page)
                .params(*options)
                .get()
                .getResponseList()
    }

    @POST
    fun create(text: String, userId: Long?=null, screenName: String?=null, vararg options: Pair<String, String?>): ResponseObject<DirectMessage> {
        return client.session.new()
                .url("/direct_messages/new.json")
                .dataAsForm("text" to text)
                .dataAsForm("user_id" to userId)
                .dataAsForm("screen_name" to screenName)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun destroy(id: Long, includeEntities: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<DirectMessage> {
        return client.session.new()
                .url("/direct_messages/destroy.json")
                .dataAsForm("id" to id)
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun createEvent(vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/direct_messages/events/new.json")
                .dataAsJson(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun createWelcomeMessage(vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/new.json")
                .dataAsJson(*options)
                .post()
                .getResponseObject()
    }

    @DELETE
    fun destroyWelcomeMessage(id: Long, vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/destroy.json")
                .param("id" to id)
                .params(*options)
                .delete()
                .getResponseObject()
    }

    @DELETE
    fun destroyWelcomeMessageRule(id: Long, vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/rules/destroy.json")
                .param("id" to id)
                .params(*options)
                .delete()
                .getResponseObject()
    }

    @POST
    fun createWelcomeMessageRule(vararg options: Pair<String, String>): ResponseObject<Empty> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/rules/new.json")
                .dataAsJson(*options)
                .post()
                .getResponseObject()
    }

    @GET
    fun getEvent(id: Long, vararg options: Pair<String, String?>): ResponseObject<DirectMessageEvent> {
        return client.session.new()
                .url("/direct_messages/events/show.json")
                .param("id" to id)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getEvents(count: Int?=null, vararg options: Pair<String, String?>): ResponseList<DirectMessageEvent> {
        return client.session.new()
                .url("/direct_messages/events/list.json")
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getWelcomeMessage(id: Long, vararg options: Pair<String, String?>): ResponseObject<WelcomeMessage> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/show.json")
                .param("id" to id)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getWelcomeMessages(count: Int?=null, vararg options: Pair<String, String?>): ResponseList<WelcomeMessage> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/list.json")
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getWelcomeMessageRule(id: Long, vararg options: Pair<String, String?>): ResponseObject<WelcomeMessageRule> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/rules/show.json")
                .param("id" to id)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET @Cursorable
    fun getWelcomeMessageRules(count: Int?=null, vararg options: Pair<String, String?>): ResponseList<WelcomeMessageRule> {
        return client.session.new()
                .url("/direct_messages/welcome_messages/rules/list.json")
                .param("count" to count)
                .params(*options)
                .get()
                .getResponseList()
    }
}