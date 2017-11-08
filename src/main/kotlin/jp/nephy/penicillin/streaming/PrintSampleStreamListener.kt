package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status

@Suppress("UNUSED")
class PrintSampleStreamListener : ISampleStreamListener {
    override fun onStatus(status: Status) {
        println("Tweet: ${status.fullText()} by @${status.user.screenName}")
    }

    override fun onDelete(delete: Delete) {
        println("Delete: User ID: ${delete.userId} deleted Status ID: ${delete.statusId}.")
    }

    override fun onUnknownData(data: String) {
        println("Unknown Data: $data")
    }
}