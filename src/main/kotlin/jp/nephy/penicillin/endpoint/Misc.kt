package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.annotation.UndocumentedAPI
import jp.nephy.penicillin.model.Contributees
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.model.ExtendedProfile
import jp.nephy.penicillin.model.Guest
import jp.nephy.penicillin.response.ResponseList
import jp.nephy.penicillin.response.ResponseObject

class Misc(private val client: Client) {
    @GET @UndocumentedAPI
    fun getContributees(vararg options: Pair<String, String?>): ResponseList<Contributees> {
        return client.session.new()
                .url("/users/contributees.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @UndocumentedAPI
    fun getPendingContributees(vararg options: Pair<String, String?>): ResponseList<Contributees> {
        return client.session.new()
                .url("/users/contributees/pending.json")
                .params(*options)
                .get()
                .getResponseList()
    }

    @GET @UndocumentedAPI
    fun activateGuest(vararg options: Pair<String, String?>): ResponseObject<Guest> {
        return client.session.new()
                .url("/guest/activate.json")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST @UndocumentedAPI
    fun setPushDestination(token: String, uuid: String, appVersion: Int=28, deviceModel: String="iPhone", deviceName: String="iPhone 6s+", environment: Int=3, lang: String="ja", systemName: String="iOS", systemVersion: String="10.2", vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/push_destinations.json")
                .dataAsForm("token" to token)
                .dataAsForm("uuid" to uuid)
                .dataAsForm("app_version" to appVersion)
                .dataAsForm("device_model" to deviceModel)
                .dataAsForm("device_name" to deviceName)
                .dataAsForm("environment" to environment)
                .dataAsForm("lang" to lang)
                .dataAsForm("system_name" to systemName)
                .dataAsForm("system_version" to systemVersion)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @GET @UndocumentedAPI
    fun getExtendedProfile(screenName: String, includeBirthdate: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<ExtendedProfile> {
        return client.session.new()
                .url("/users/extended_profile.json")
                .param("screen_name" to screenName)
                .param("include_birthdate" to includeBirthdate)
                .params(*options)
                .get()
                .getResponseObject()
    }
}
