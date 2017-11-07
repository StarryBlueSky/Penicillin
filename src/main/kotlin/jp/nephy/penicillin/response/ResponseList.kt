package jp.nephy.penicillin.response

import okhttp3.Request
import okhttp3.Response

class ResponseList<T>(content: String, request: Request, response: Response) : AbsRESTResponseList<T>(content, request, response)
