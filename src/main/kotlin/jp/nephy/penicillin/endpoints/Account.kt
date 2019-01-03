@file:Suppress("UNUSED")

package jp.nephy.penicillin.endpoints

import jp.nephy.penicillin.PenicillinClient
import jp.nephy.penicillin.core.request.action.PenicillinMultipleJsonObjectActions
import jp.nephy.penicillin.endpoints.parameters.MediaType
import jp.nephy.penicillin.models.Media
import jp.nephy.penicillin.models.Setting
import jp.nephy.penicillin.models.User
import jp.nephy.penicillin.models.VerifyCredentials

class Account(override val client: PenicillinClient): Endpoint {
    @PrivateEndpoint
    fun settings(vararg options: Pair<String, Any?>) = client.session.get("/1.1/account/settings.json") {
        parameter("include_alt_text_compose" to "true", "include_mention_filter" to "true", "include_ranked_timeline" to "true", "include_universal_quality_filtering" to "true", *options)
    }.jsonObject<Setting>()

    fun verifyCredentials(includeEntities: Boolean? = null, skipStatus: Boolean? = null, includeEmail: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.get("/1.1/account/verify_credentials.json") {
            parameter("include_entities" to includeEntities, "skip_status" to skipStatus, "include_email" to includeEmail, *options)
        }.jsonObject<VerifyCredentials>()

    fun removeProfileBanner(vararg options: Pair<String, Any?>) = client.session.post("/1.1/account/remove_profile_banner.json") {
        body {
            form {
                add(*options)
            }
        }
    }.empty()

    fun updateSettings(
        sleepTimeEnabled: Boolean? = null,
        startSleepTime: Int? = null,
        endSleepTime: Int? = null,
        timeZone: String? = null,
        trendLocationWoeid: Int? = null,
        lang: String? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.post("/1.1/account/settings.json") {
        body {
            form {
                add(
                    "sleep_time_enabled" to sleepTimeEnabled,
                    "start_sleep_time" to startSleepTime,
                    "end_sleep_time" to endSleepTime,
                    "time_zone" to timeZone,
                    "trend_location_woeid" to trendLocationWoeid,
                    "lang" to lang,
                    *options
                )
            }
        }
    }.jsonObject<Setting>()

    fun updateProfile(
        name: String? = null,
        url: String? = null,
        location: String? = null,
        description: String? = null,
        profileLinkColor: String? = null,
        includeEntities: Boolean? = null,
        skipStatus: Boolean? = null,
        birthdateYear: Int? = null,
        birthdateMonth: Int? = null,
        birthdateDay: Int? = null,
        vararg options: Pair<String, Any?>
    ) = client.session.post("/1.1/account/update_profile.json") {
        body {
            form {
                add(
                    "name" to name,
                    "url" to url,
                    "location" to location,
                    "description" to description,
                    "profile_link_color" to profileLinkColor,
                    "include_entities" to includeEntities,
                    "skip_status" to skipStatus,
                    "birthdate_year" to birthdateYear,
                    "birthdate_month" to birthdateMonth,
                    "birthdate_day" to birthdateDay,
                    *options
                )
            }
        }
    }.jsonObject<User>()

    fun updateProfileBackgroundImage(
        data: ByteArray, mediaType: MediaType, tile: Boolean? = null, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>
    ): PenicillinMultipleJsonObjectActions<Media> {
        return client.media.uploadMedia(data, mediaType) + { results ->
            client.session.post("/1.1/account/update_profile_background_image.json") {
                body {
                    form {
                        add("tile" to tile, "include_entities" to includeEntities, "skip_status" to skipStatus, "media_id" to results.first.result.mediaId, *options)
                    }
                }
            }.jsonObject<User>()
        }
    }

    fun updateProfileBanner(file: ByteArray, mediaType: MediaType, width: Int? = null, height: Int? = null, offsetLeft: Int? = null, offsetTop: Int? = null, vararg options: Pair<String, Any?>) =
        client.session.post("/1.1/account/update_profile_banner.json") {
            body {
                multiPart {
                    add("banner", "blob", mediaType.contentType, file)
                    add("width" to width, "height" to height, "offset_left" to offsetLeft, "offset_top" to offsetTop, *options)
                }
            }
        }.empty()

    fun updateProfileImage(file: ByteArray, mediaType: MediaType, includeEntities: Boolean? = null, skipStatus: Boolean? = null, vararg options: Pair<String, Any?>) =
        client.session.post("/1.1/account/update_profile_image.json") {
            body {
                multiPart {
                    add("image", "blob", mediaType.contentType, file)
                    add("include_entities" to includeEntities, "skip_status" to skipStatus, *options)
                }
            }
        }.jsonObject<User>()
}
