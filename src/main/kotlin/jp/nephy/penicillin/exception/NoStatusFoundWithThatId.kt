package jp.nephy.penicillin.exception

class NoStatusFoundWithThatId: AbsTwitterErrorMessage(144, "No status found with that ID.", "Corresponds with HTTP 404 - the requested Tweet ID is not found (if it existed, it was probably deleted)")