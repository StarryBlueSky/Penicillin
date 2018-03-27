package jp.nephy.penicillin.exception

class YouCantMuteYourself: AbstractTwitterErrorMessage(271, "You can’t mute yourself.", "Corresponds with HTTP 403. The authenticated user account cannot mute itself.")
