package jp.nephy.penicillin.exception

class ThisApplicationIsNotAllowedToAccessOrDeleteYourDirectMessages: AbsTwitterErrorMessage(93, "This application is not allowed to access or delete your direct messages", "Corresponds with HTTP 403. The OAuth token does not provide access to Direct Messages.")