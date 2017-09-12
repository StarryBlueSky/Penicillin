package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.model.Configuration
import jp.nephy.penicillin.model.Language
import jp.nephy.penicillin.model.Privacy
import jp.nephy.penicillin.model.Tos
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class Help(private val client: Client) {
    @GET
    fun getConfiguration(vararg options: Pair<String, String?>): ResponseObject<Configuration> {
        return client.session.new()
                .url("/help/configuration.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getLanguages(vararg options: Pair<String, String?>): ResponseList<Language> {
        return client.session.new()
                .url("/help/languages.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET
    fun getPrivacy(vararg options: Pair<String, String?>): ResponseObject<Privacy> {
        return client.session.new()
                .url("/help/privacy.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun getTos(vararg options: Pair<String, String?>): ResponseObject<Tos> {
        return client.session.new()
                .url("/help/tos.json")
                .params(*options)
                .get()
                .getResponseObject()
    }
}
