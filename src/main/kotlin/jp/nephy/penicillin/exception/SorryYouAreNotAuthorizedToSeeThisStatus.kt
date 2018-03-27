package jp.nephy.penicillin.exception

class SorryYouAreNotAuthorizedToSeeThisStatus: AbstractTwitterErrorMessage(179, "Sorry, you are not authorized to see this status", "Corresponds with HTTP 403 — thrown when a Tweet cannot be viewed by the authenticating user, usually due to the Tweet’s author having protected their Tweets.")
