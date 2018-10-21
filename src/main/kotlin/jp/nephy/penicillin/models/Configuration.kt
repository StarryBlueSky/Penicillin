@file:Suppress("UNUSED")

package jp.nephy.penicillin.models

import jp.nephy.jsonkt.ImmutableJsonObject
import jp.nephy.jsonkt.delegation.*


data class Configuration(override val json: ImmutableJsonObject): PenicillinModel {
    val charactersReservedPerMedia by int("characters_reserved_per_media")
    val clientEventUrl by string("client_event_url")
    val dmTextCharacterLimit by int("dm_text_character_limit")
    val maxMediaPerUpload by int("max_media_per_upload")
    val nonUsernamePaths by stringList("non_username_paths")
    val photoSizeLimit by int("photo_size_limit")
    val photoSizes by model<Photo>(key = "photo_sizes")
    val shortUrlLength by int("short_url_length")
    val shortUrlLengthHttps by int("short_url_length_https")
}
