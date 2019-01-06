package jp.nephy.penicillin.core.streaming.handler

import jp.nephy.jsonkt.JsonObject
import jp.nephy.penicillin.core.streaming.listener.FilterStreamListener
import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class FilterStreamHandler(override val listener: FilterStreamListener): StreamHandler<FilterStreamListener> {
    override suspend fun handle(json: JsonObject, scope: CoroutineScope) {
        scope.launch(scope.coroutineContext) {
            when {
                "text" in json -> {
                    listener.onStatus(Status(json))
                }
                "delete" in json -> {
                    listener.onDelete(Stream.Delete(json))
                }
                else -> {
                    listener.onUnhandledJson(json)
                }
            }
        }

        listener.onAnyJson(json)
    }
}
