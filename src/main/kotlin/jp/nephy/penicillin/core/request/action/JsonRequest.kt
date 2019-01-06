package jp.nephy.penicillin.core.request.action

import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

interface JsonRequest<M: PenicillinModel> {
    val model: KClass<M>
}
