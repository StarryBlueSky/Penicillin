package jp.nephy.penicillin.core.streaming.listener

import jp.nephy.penicillin.models.Status
import jp.nephy.penicillin.models.Stream

interface SampleStreamListener: StreamListener {
    suspend fun onStatus(status: Status) {}
    suspend fun onDelete(delete: Stream.Delete) {}
}
