package jp.nephy.penicillin.exception

class UnableToVerifyYourCredentials: AbsTwitterErrorMessage(99, "Unable to verify your credentials.", "Corresponds with HTTP 403. The OAuth credentials cannot be validated. Check that the token is still valid.")