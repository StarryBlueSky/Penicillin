package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byPhoto
import jp.nephy.penicillin.converter.byStringArray
import jp.nephy.penicillin.converter.byURL

class Configuration(val json: JsonElement) {
    val charactersReservedPerMedia by json.byInt("characters_reserved_per_media")
    val clientEventUrl by json.byURL("client_event_url")
    val dmTextCharacterLimit by json.byInt("dm_text_character_limit")
    val maxMediaPerUpload by json.byInt("max_media_per_upload")
    val nonUsernamePaths by json.byStringArray("non_username_paths")
    val photoSizeLimit by json.byInt("photo_size_limit")
    val photoSizes by json.byPhoto("photo_sizes")
    val shortUrlLength by json.byInt("short_url_length")
    val shortUrlLengthHttps by json.byInt("short_url_length_https")
}
