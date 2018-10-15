package jp.nephy.penicillin.models

import jp.nephy.jsonkt.delegation.lambda
import jp.nephy.jsonkt.delegation.model
import jp.nephy.jsonkt.delegation.string
import jp.nephy.jsonkt.string
import jp.nephy.penicillin.models.special.CreatedAt

abstract class UserStreamEvent: PenicillinModel {
    val event by string
    val source by model<User>()
    val target by model<User>()
    val createdAt by lambda("created_at") { CreatedAt(it.string) }
}
