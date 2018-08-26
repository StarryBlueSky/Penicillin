package jp.nephy.penicillin.models

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*


class SearchTypeahead(override val json: JsonObject): PenicillinModel {
    val completedIn by json.byFloat("completed_in")
    val hashtags by json.byStringList
    val numResults by json.byInt("num_results")
    val oneclick by json.byStringList
    val query by json.byString
    val topics by json.byModelList<Topic>()

    val users by json.byModelList<UserTypeahead>()
}
