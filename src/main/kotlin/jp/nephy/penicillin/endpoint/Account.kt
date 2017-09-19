package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.Client
import jp.nephy.penicillin.annotation.GET
import jp.nephy.penicillin.annotation.POST
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.model.Setting
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.model.VerifyCredentials
import jp.nephy.penicillin.parameters.MediaType
import jp.nephy.penicillin.response.ResponseObject
import java.net.URL

class Account(private val client: Client) {
    @GET
    fun getSettings(vararg options: Pair<String, String?>): ResponseObject<Setting> {
        return client.session.new()
                .url("/account/settings.json")
                .paramIfOfficial("include_alt_text_compose" to "true")
                .paramIfOfficial("include_mention_filter" to "true")
                .paramIfOfficial("include_ranked_timeline" to "true")
                .paramIfOfficial("include_universal_quality_filtering" to "true")
                .params(*options)
                .get()
                .getResponseObject()
    }

    @GET
    fun verifyCredentials(includeEntities: Boolean?=null, skipStatus: Boolean?=null, includeEmail: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<VerifyCredentials> {
        return client.session.new()
                .url("/account/verify_credentials.json")
                .param("include_entities" to includeEntities)
                .param("skip_status" to skipStatus)
                .param("include_email" to includeEmail)
                .params(*options)
                .get()
                .getResponseObject()
    }

    @POST
    fun removeProfileBanner(vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/account/remove_profile_banner.json")
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun updateSettings(sleepTimeEnabled: Boolean?=null, startSleepTime: Int?=null, endSleepTime: Int?=null, timeZone: String?=null, trendLocationWoeid: Int?=null, lang: String?=null, vararg options: Pair<String, String?>): ResponseObject<Setting> {
        return client.session.new()
                .url("/account/settings.json")
                .dataAsForm("sleep_time_enabled" to sleepTimeEnabled)
                .dataAsForm("start_sleep_time" to startSleepTime)
                .dataAsForm("end_sleep_time" to endSleepTime)
                .dataAsForm("time_zone" to timeZone)
                .dataAsForm("trend_location_woeid" to trendLocationWoeid)
                .dataAsForm("lang" to lang)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun updateProfile(name: String?=null, url: URL?=null, location: String?=null, description: String?=null, profileLinkColor: String?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/account/update_profile.json")
                .dataAsForm("name" to name)
                .dataAsForm("url" to url)
                .dataAsForm("location" to location)
                .dataAsForm("description" to description)
                .dataAsForm("profile_link_color" to profileLinkColor)
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm("skip_status" to skipStatus)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun updateProfileBackgroundImage(file: ByteArray, mediaType: MediaType, tile: Boolean?=null, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        val upload = client.media.uploadMedia(file, mediaType)

        return client.session.new()
                .url("/account/update_profile_background_image.json")
                .dataAsForm("tile" to tile)
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm("skip_status" to skipStatus)
                .dataAsForm("media_id" to upload.result.mediaId)
                .dataAsForm(*options)
                .post()
                .getResponseObject()
    }

    @POST
    fun updateProfileBanner(file: ByteArray, mediaType: MediaType, width: Int?=null, height: Int?=null, offsetLeft: Int?=null, offsetTop: Int?=null, vararg options: Pair<String, String?>): ResponseObject<Empty> {
        return client.session.new()
                .url("/account/update_profile_banner.json")
                .dataAsForm("width" to width)
                .dataAsForm("height" to height)
                .dataAsForm("offset_left" to offsetLeft)
                .dataAsForm("offset_top" to offsetTop)
                .dataAsForm(*options)
                .file(file, mediaType.toMIMEType(), "banner")
                .post()
                .getResponseObject()
    }

    @POST
    fun updateProfileImage(file: ByteArray, mediaType: MediaType, includeEntities: Boolean?=null, skipStatus: Boolean?=null, vararg options: Pair<String, String?>): ResponseObject<User> {
        return client.session.new()
                .url("/account/update_profile_image.json")
                .dataAsForm("include_entities" to includeEntities)
                .dataAsForm("skip_status" to skipStatus)
                .dataAsForm(*options)
                .file(file, mediaType.toMIMEType(), "image")
                .post()
                .getResponseObject()
    }
}
