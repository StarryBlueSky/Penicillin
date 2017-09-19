package jp.nephy.penicillin.exception

class TheTextOfYourDirectMessageIsOverTheMaxCharacterLimit: AbsTwitterErrorMessage(354, "The text of your direct message is over the max character limit.", "Corresponds with HTTP 403. The message size exceeds the number of characters permitted in a direct message.")