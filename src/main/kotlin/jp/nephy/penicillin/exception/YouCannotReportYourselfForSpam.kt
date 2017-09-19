package jp.nephy.penicillin.exception

class YouCannotReportYourselfForSpam: AbsTwitterErrorMessage(36, "You cannot report yourself for spam.", "Corresponds with HTTP 403. You cannot use your own user ID in a report spam call.")