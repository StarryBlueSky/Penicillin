package jp.nephy.penicillin.exception

class SslIsRequired: AbstractTwitterErrorMessage(92, "SSL is required", "Only SSL connections are allowed in the API, you should update your request to a secure connection. See how to connect using TLS")
