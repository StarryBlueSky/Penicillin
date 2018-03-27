package jp.nephy.penicillin.exception

class YourCredentialsDoNotAllowAccessToThisResource: AbstractTwitterErrorMessage(220, "Your credentials do not allow access to this resource.", "Corresponds with HTTP 403. The authentication token in use is restricted and cannot access the requested resource.")
