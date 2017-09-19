package jp.nephy.penicillin.exception

class BadAuthenticationData: AbsTwitterErrorMessage(215, "Bad authentication data", "Typically sent with 1.1 responses with HTTP code 400. The method requires authentication but it was not presented or was wholly invalid.")