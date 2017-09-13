package jp.nephy.penicillin.streaming

import jp.nephy.penicillin.model.Delete
import jp.nephy.penicillin.model.Status

interface IFilterStreamListener {
    fun onStatus(status: Status)

    fun onDelete(delete: Delete)

    fun onUnknownData(data: String)
}