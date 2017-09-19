package jp.nephy.penicillin.exception

class YouAttemptedToReplyToATweetThatIsDeletedOrNotVisibleToYou: AbsTwitterErrorMessage(385, "You attempted to reply to a tweet that is deleted or not visible to you.", "Corresponds with HTTP 403. A reply can only be sent with reference to an existing public Tweet.")