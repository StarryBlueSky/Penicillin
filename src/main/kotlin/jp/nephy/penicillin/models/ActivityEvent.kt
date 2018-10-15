package jp.nephy.penicillin.models

import jp.nephy.jsonkt.JsonObject
import jp.nephy.jsonkt.delegation.int
import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.modelList
import jp.nephy.jsonkt.delegation.string
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt

data class ActivityEvent(override val json: JsonObject): PenicillinModel {
    val action by string
    val maxPosition by string("max_position")
    val minPosition by string("min_position")
    val createdAt by lambda("created_at") { CreatedAt(it.string) }
    val targetObjects by modelList<Status>(key = "target_objects")
    val targetObjectsSize by int("target_objects_size")
    val targets by modelList<User>()
    val targetsSize by int("targets_size")
    val sources by modelList<User>()
    val sourcesSize by int("sources_size")
}
