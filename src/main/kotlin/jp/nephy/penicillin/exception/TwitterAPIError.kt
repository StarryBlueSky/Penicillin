package jp.nephy.penicillin.exception

class TwitterAPIError(msg: String, val content: String, val code: Int?=null, message: String?=null): Exception() {
    override var message = message ?: "$msg\n`$content` returned."
}
