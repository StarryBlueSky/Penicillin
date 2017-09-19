package jp.nephy.penicillin.exception

class TwitterAPIError(msg: String, content: String, code: Int?=null, errorMessage: String?=null): Exception() {
    override var message = errorMessage ?: "$msg\n`$content` returned."

    init {
        if (code != null) {
            throw when (code) {
                32 -> CouldNotAuthenticateYou()
                34 -> SorryThatPageDoesNotExist()
                36 -> YouCannotReportYourselfForSpam()
                44 -> AttachmentUrlParameterIsInvalid()
                50 -> UserNotFound()
                63 -> UserHasBeenSuspended()
                64 -> YourAccountIsSuspendedAndIsNotPermittedToAccessThisFeature()
                68 -> TheTwitterRestApiV1IsNoLongerActivePleaseMigrateToApiV11()
                87 -> ClientIsNotPermittedToPerformThisAction()
                88 -> RateLimitExceeded()
                89 -> InvalidOrExpiredToken()
                92 -> SslIsRequired()
                93 -> ThisApplicationIsNotAllowedToAccessOrDeleteYourDirectMessages()
                99 -> UnableToVerifyYourCredentials()
                130 -> OverCapacity()
                131 -> InternalError()
                135 -> TimestampOutOfBounds()
                144 -> NoStatusFoundWithThatId()
                150 -> YouCannotSendMessagesToUsersWhoAreNotFollowingYou()
                151 -> ThereWasAnErrorSendingYourMessage()
                161 -> YouAreUnableToFollowMorePeopleAtThisTime()
                179 -> SorryYouAreNotAuthorizedToSeeThisStatus()
                185 -> UserIsOverDailyStatusUpdateLimit()
                187 -> StatusIsADuplicate()
                205 -> YouAreOverTheLimitForSpamReports()
                215 -> BadAuthenticationData()
                220 -> YourCredentialsDoNotAllowAccessToThisResource()
                226 -> ThisRequestLooksLikeItMightBeAutomated()
                231 -> UserMustVerifyLogin()
                251 -> ThisEndpointHasBeenRetiredAndShouldNotBeUsed()
                261 -> ApplicationCannotPerformWriteActions()
                271 -> YouCantMuteYourself()
                272 -> YouAreNotMutingTheSpecifiedUser()
                323 -> AnimatedGifsAreNotAllowedWhenUploadingMultipleImages()
                324 -> TheValidationOfMediaIdsFailed()
                325 -> AMediaIdWasNotFound()
                326 -> ToProtectOurUsersFromSpamAndOtherMaliciousActivityThisAccountIsTemporarilyLocked()
                354 -> TheTextOfYourDirectMessageIsOverTheMaxCharacterLimit()
                385 -> YouAttemptedToReplyToATweetThatIsDeletedOrNotVisibleToYou()
                386 -> TheTweetExceedsTheNumberOfAllowedAttachmentTypes()
                else -> UnknownTwitterError(code, errorMessage ?: "")
            }
        }
    }
}
