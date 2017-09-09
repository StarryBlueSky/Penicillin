package jp.nephy.penicillin.api

import jp.nephy.penicillin.request.HTTPMethod
import java.net.URL

interface IEndPoint {
    val method: HTTPMethod
    val resourceUrl: String
    val responseFormat: ResponseFormats
    val isRateLimited: Boolean
    val defaultParameter: Parameter

    fun getResourceURL(): URL {
        return URL(resourceUrl)
    }
}
