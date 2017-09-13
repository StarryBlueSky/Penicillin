package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status

class PrintFilterStreamListener : IFilterStreamListener {
    override fun onStatus(status: Status) {
        println("Tweet: ${status.text} by @${status.user.screenName}")
    }

    override fun onDelete(delete: Delete) {
        println("Delete: User ID: ${delete.userId} deleted Status ID: ${delete.statusId}.")
    }

    override fun onUnknownData(data: String) {
        println("Unknown Data: $data")
    }
}