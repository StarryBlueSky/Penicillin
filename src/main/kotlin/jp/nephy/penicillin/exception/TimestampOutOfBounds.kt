package jp.nephy.penicillin.exception

class TimestampOutOfBounds: AbsTwitterErrorMessage(135, "Could not authenticate you", "Corresponds with HTTP 401 - Timestamp out of bounds (check your system clock)")