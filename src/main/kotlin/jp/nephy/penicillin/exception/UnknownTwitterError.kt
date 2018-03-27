package jp.nephy.penicillin.exception

class UnknownTwitterError(code: Int, message: String): AbstractTwitterErrorMessage(code, message, null)
