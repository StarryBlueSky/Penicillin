package jp.nephy.penicillin.exception

class UserIsOverDailyStatusUpdateLimit: AbstractTwitterErrorMessage(185, "User is over daily status update limit", "Corresponds with HTTP 403 — thrown when a Tweet cannot be posted due to the user having no allowance remaining to post. Despite the text in the error message indicating that this error is only thrown when a daily limit is reached, this error will be thrown whenever a posting limitation has been reached. Posting allowances have roaming windows of time of unspecified duration.")
