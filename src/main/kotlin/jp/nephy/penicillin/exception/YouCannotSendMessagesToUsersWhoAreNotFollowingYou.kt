package jp.nephy.penicillin.exception

class YouCannotSendMessagesToUsersWhoAreNotFollowingYou: AbsTwitterErrorMessage(150, "You cannot send messages to users who are not following you.", "Corresponds with HTTP 403 — sending a Direct Message failed.")