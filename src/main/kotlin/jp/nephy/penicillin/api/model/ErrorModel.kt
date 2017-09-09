package jp.nephy.penicillin.api.model

import com.github.salomonbrys.kotson.byArray
import com.google.gson.JsonElement

class ErrorModel(val json: JsonElement) {
    val errors by json.byArray //{ "code": 93,"message": "This application is not allowed to access or delete your direct messages." }
}
