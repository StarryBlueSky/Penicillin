package jp.nephy.penicillin.exception

class AMediaIdWasNotFound: AbsTwitterErrorMessage(325, "A media id was not found.", "Corresponds with HTTP 400. The media ID attached to the Tweet was not found.")