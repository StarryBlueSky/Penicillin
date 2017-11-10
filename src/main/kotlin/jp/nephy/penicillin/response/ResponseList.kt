package jp.nephy.penicillin.response

import okhttp3.Request
import okhttp3.Response

@Suppress("UNUSED")
class ResponseList<T>(content: String, request: Request, response: Response) : AbsRESTResponseList<T>(content, request, response)
