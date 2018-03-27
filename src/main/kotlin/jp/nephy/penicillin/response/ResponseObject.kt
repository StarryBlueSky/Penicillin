package jp.nephy.penicillin.response

import jp.nephy.jsonkt.JsonModel
import okhttp3.Request
import okhttp3.Response


class ResponseObject<out T: JsonModel>(val result: T, content: String, request: Request, response: Response): AbstractRestResponse(content, request, response)
