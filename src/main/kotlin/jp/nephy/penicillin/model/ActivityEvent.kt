package jp.nephy.penicillin.model

import com.github.salomonbrys.kotson.byInt
import com.github.salomonbrys.kotson.byString
import com.google.gson.JsonElement
import jp.nephy.penicillin.converter.byConverter
import jp.nephy.penicillin.converter.byList
import jp.nephy.penicillin.misc.CreatedAt

class ActivityEvent(val json: JsonElement) {
    val action by json.byString
    val maxPosition by json.byString("max_position")
    val minPosition by json.byString("min_position")
    val createdAt by json.byConverter<String, CreatedAt>("created_at")
    val targetObjects by json.byList<Status>("target_objects")
    val targetObjectsSize by json.byInt("target_objects_size")
    val targets by json.byList<User>()
    val targetsSize by json.byInt("targets_size")
    val sources by json.byList<User>()
    val sourcesSize by json.byInt("sources_size")
}
