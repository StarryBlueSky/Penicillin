package jp.nephy.penicillin.exception

class ThereWasAnErrorSendingYourMessage: AbstractTwitterErrorMessage(151, "There was an error sending your message: reason", "Corresponds with HTTP 403 — sending a Direct Message failed. The reason value will provide more information.")
