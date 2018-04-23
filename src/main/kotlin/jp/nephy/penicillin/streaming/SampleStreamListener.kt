package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status

interface SampleStreamListener: StreamListener<SampleStreamHandler> {
    fun onStatus(status: Status) {}
    fun onDelete(delete: Delete) {}
    fun onUnknownData(data: String) {}
}
