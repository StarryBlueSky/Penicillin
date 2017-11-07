package jp.nephy.penicillin.response

import okhttp3.Request
import okhttp3.Response

class ResponseText(content: String, request: Request, response: Response): AbsRESTResponse(content, request, response)
