package jp.nephy.penicillin.model

import com.google.gson.JsonObject
import jp.nephy.jsonkt.*
import jp.nephy.penicillin.misc.CreatedAt

@Suppress("UNUSED")
class ActivityEvent(override val json: JsonObject): JsonModel {
    val action by json.byString
    val maxPosition by json.byString("max_position")
    val minPosition by json.byString("min_position")
    val createdAt by json.byLambda("created_at") { CreatedAt(string) }
    val targetObjects by json.byModelList<Status>(key = "target_objects")
    val targetObjectsSize by json.byInt("target_objects_size")
    val targets by json.byModelList<User>()
    val targetsSize by json.byInt("targets_size")
    val sources by json.byModelList<User>()
    val sourcesSize by json.byInt("sources_size")
}
