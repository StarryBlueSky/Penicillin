package jp.nephy.penicillin.exception

class YourAccountIsSuspendedAndIsNotPermittedToAccessThisFeature: AbstractTwitterErrorMessage(64, "Your account is suspended and is not permitted to access this feature", "Corresponds with HTTP 403 — the access token being used belongs to a suspended user and they can’t complete the action you’re trying to take.")
