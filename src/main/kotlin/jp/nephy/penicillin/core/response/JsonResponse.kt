package jp.nephy.penicillin.core.response

import jp.nephy.jsonkt.JsonElement
import jp.nephy.penicillin.models.PenicillinModel
import kotlin.reflect.KClass

interface JsonResponse<M: PenicillinModel, T: JsonElement> {
    val model: KClass<M>
    val json: T
}
