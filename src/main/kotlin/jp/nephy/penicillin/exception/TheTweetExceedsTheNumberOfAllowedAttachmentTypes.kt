package jp.nephy.penicillin.exception

class TheTweetExceedsTheNumberOfAllowedAttachmentTypes: AbsTwitterErrorMessage(386, "The Tweet exceeds the number of allowed attachment types.", "Corresponds with HTTP 403. A Tweet is limited to a single attachment resource (media, Quote Tweet, etc.)")