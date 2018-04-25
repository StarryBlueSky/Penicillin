package jp.nephy.penicillin.endpoint

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.endpoint.parameter.MediaType
import jp.nephy.penicillin.model.Empty
import jp.nephy.penicillin.model.Setting
import jp.nephy.penicillin.model.User
import jp.nephy.penicillin.model.VerifyCredentials


class Account(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun settings(vararg options: Pair<String, Any?>) = client.session.getObject<Setting>("/account/settings.json") {
        query("include_alt_text_compose" to "true", "include_mention_filter" to "true", "include_ranked_timeline" to "true", "include_universal_quality_filtering" to "true", *options)
    }

    fun verifyCredentials(includeEntities: Boolean? = null, skipStatus: Boolean? = null, includeEmail: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.getObject<VerifyCredentials>("/account/verify_credentials.json") {
        query("include_entities" to includeEntities, "skip_status" to skipStatus, "include_email" to includeEmail, *options)
    }

    fun removeProfileBanner(vararg options: Pair<String, Any?>) = client.session.postObject<Empty>("/account/remove_profile_banner.json") {
        form(*options)
    }

    fun updateSettings(sleepTimeEnabled: Boolean? = null, startSleepTime: Int? = null, endSleepTime: Int? = null, timeZone: String? = null, trendLocationWoeid: Int? = null, lang: String? = null, vararg options: Pair<String, Any?>) = client.session.postObject<Setting>("/account/settings.json") {
        form("sleep_time_enabled" to sleepTimeEnabled, "start_sleep_time" to startSleepTime, "end_sleep_time" to endSleepTime, "time_zone" to timeZone, "trend_location_woeid" to trendLocationWoeid, "lang" to lang, *options)
    }

    fun updateProfile(name: String? = null, url: String? = null, location: String? = null, description: String? = null, profileLinkColor: String? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, birthdateYear: Int? = null, birthdateMonth: Int? = null, birthdateDay: Int? = null, vararg options: Pair<String, Any?>) = client.session.postObject<User>("/account/update_profile.json") {
        form("name" to name, "url" to url, "location" to location, "description" to description, "profile_link_color" to profileLinkColor, "include_entities" to includeEntities, "skip_status" to skipStatus, "birthdate_year" to birthdateYear, "birthdate_month" to birthdateMonth, "birthdate_day" to birthdateDay, *options)
    }

    fun updateProfileBackgroundImage(file: ByteArray, mediaType: MediaType, tile: Boolean? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.postObject<User>("/account/update_profile_background_image.json") {
        val upload = client.media.uploadMedia(file, mediaType).complete()
        form("tile" to tile, "include_entities" to includeEntities, "skip_status" to skipStatus, "media_id" to upload.result.mediaId, *options)
    }

    fun updateProfileBanner(file: ByteArray, mediaType: MediaType, width: Int? = null, height: Int? = null, offsetLeft: Int? = null, offsetTop: Int? = null, vararg options: Pair<String, Any?>) = client.session.postObject<Empty>("/account/update_profile_banner.json") {
        form("width" to width, "height" to height, "offset_left" to offsetLeft, "offset_top" to offsetTop, *options)
        file(file, mediaType.mimeType, "banner")
    }

    fun updateProfileImage(file: ByteArray, mediaType: MediaType, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) = client.session.postObject<User>("/account/update_profile_image.json") {
        form("include_entities" to includeEntities, "skip_status" to skipStatus, *options)
        file(file, mediaType.mimeType, "image")
    }
}
