@file:Suppress("UNUSED")

package jp.nephy.penicillin.extensions

import jp.nephy.penicillin.core.response.ApiResponse

val ApiResponse.responseTimeMs: Int?
    get() = response.headers["x-response-time"]?.toIntOrNull()
