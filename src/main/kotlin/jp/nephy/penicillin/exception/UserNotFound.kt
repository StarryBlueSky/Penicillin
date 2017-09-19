package jp.nephy.penicillin.exception

class UserNotFound: AbsTwitterErrorMessage(50, "User not found.", "Corresponds with HTTP 404. The user is not found.")