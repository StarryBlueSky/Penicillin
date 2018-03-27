package jp.nephy.penicillin.exception

class YouAreOverTheLimitForSpamReports: AbstractTwitterErrorMessage(205, "You are over the limit for spam reports.", "Corresponds with HTTP 403. The account limit for reporting spam has been reached. Try again later.")
