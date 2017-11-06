package jp.nephy.penicillin.exception

class UnknownTwitterError(code: Int, message: String): AbsTwitterErrorMessage(code, message, null)
