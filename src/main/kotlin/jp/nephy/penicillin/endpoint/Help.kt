package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.*
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

    @GET @UndocumentedAPI
    fun getSetting(includeZeroRate: Boolean?=true, settingsVersion: String?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/help/settings.json")
                .param("include_zero_rate" to includeZeroRate)
                .param("settings_version" to settingsVersion)
                .params(*options)
                .get()
                .getResponseObject()
    }
}
