package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byBool
import com.github.salomonbrys.kotson.byString
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import java.net.URL

@Suppress("UNUSED")
class CoverFormat(json: JsonElement): CoverMedia(json) {
    val pageId by json.byString("page_id")
    val isPromoted by json.byBool("is_promoted")
    val linkUrl by json["link_title_card"].byConverter<String, URL>("url")
    val linkDisplayUrl by json["link_title_card"].byString("display_url")
    val linkVanitySource by json["link_title_card"].byString("vanity_source")
    val linkTitle by json["link_title_card"].byString("title")
}