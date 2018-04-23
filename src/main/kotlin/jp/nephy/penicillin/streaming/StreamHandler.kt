package jp.nephy.penicillin.streaming

import com.google.gson.JsonObject

interface StreamHandler {
    fun handle(json: JsonObject)
}
