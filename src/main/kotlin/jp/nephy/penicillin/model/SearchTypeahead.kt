package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byFloat
import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byList

class SearchTypeahead(val json: JsonElement) {
    val completedIn by json.byFloat("completed_in")
    val hashtags by json.byList<String>()
    val numResults by json.byInt("num_results")
    val oneclick by json.byList<String>()
    val query by json.byString
    val topics by json.byList<Topic>()

    val users by json.byList<UserTypeahead>()
}
