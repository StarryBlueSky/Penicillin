package jp.nephy.penicillin.response

import okhttp3.Request
import okhttp3.Response

class ResponseObject<out T>(val result: T, content: String, request: Request, response: Response): AbsRESTResponse(content, request, response)
