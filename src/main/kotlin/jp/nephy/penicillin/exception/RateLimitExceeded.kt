package jp.nephy.penicillin.exception

class RateLimitExceeded: AbsTwitterErrorMessage(88, "Rate limit exceeded", "The request limit for this resource has been reached for the current rate limit window.")