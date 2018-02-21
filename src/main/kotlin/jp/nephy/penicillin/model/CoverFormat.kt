package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.byBool
import jp.nephy.jsonkt.byString
import jp.nephy.jsonkt.byUrl

@Suppress("UNUSED")
class CoverFormat(json: JsonObject): CoverMedia(json) {
    val pageId by json.byString("page_id")
    val isPromoted by json.byBool("is_promoted")
    val linkUrl by json["link_title_card"].byUrl("url")
    val linkDisplayUrl by json["link_title_card"].byString("display_url")
    val linkVanitySource by json["link_title_card"].byString("vanity_source")
    val linkTitle by json["link_title_card"].byString("title")
}
