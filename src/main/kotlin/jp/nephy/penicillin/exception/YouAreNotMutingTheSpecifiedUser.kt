package jp.nephy.penicillin.exception

class YouAreNotMutingTheSpecifiedUser: AbsTwitterErrorMessage(272, "You are not muting the specified user.", "Corresponds with HTTP 403. The authenticated user account is not muting the account a call is attempting to unmute.")