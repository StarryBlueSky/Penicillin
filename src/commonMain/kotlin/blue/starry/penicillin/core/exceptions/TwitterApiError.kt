/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package blue.starry.penicillin.core.exceptions

import blue.starry.penicillin.core.i18n.LocalizedString
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

/**
 * Represents Twitter API error codes.
 * Described in https://developer.twitter.com/en/docs/basics/response-codes.
 *
 * In addition to descriptive error text, error messages contain machine-parseable codes. While the text for an error message may change, the codes will stay the same.
 * The following table describes the codes which may appear when working with the standard API (note that the Ads API and some other resource families may present additional error codes). If an error response is not listed in the table, fall back to examining the HTTP status codes above in order to determine the best way to address the issue.
 */
public data class TwitterApiError(
    /**
     * The code. Must be unique.
     */
    public val code: Int,

    /**
     * The title.
     */
    public val title: String,

    /**
     * The description.
     */
    public val description: String,

    /**
     * Corresponding HTTP status code, or null.
     */
    public val httpStatusCode: HttpStatusCode?
) {
    @Suppress("KDocMissingDocumentation", "UNUSED")
    public companion object {
        public val InvalidCoordinates: TwitterApiError = TwitterApiError(3, "Invalid coordinates.", "The coordinates provided as parameters were not valid for the request.", HttpStatusCode.BadRequest)

        public val NoLocationAssociated: TwitterApiError = TwitterApiError(13, "No location associated with the specified IP address.", "It was not possible to derive a location for the IP address provided as a parameter on the geo search request.", HttpStatusCode.NotFound)

        public val NoUserMatches: TwitterApiError = TwitterApiError(17, "No user matches for specified terms.", "It was not possible to find a user profile matching the parameters specified.", HttpStatusCode.NotFound)

        public val CouldNotAuthenticate: TwitterApiError = TwitterApiError(32, "Could not authenticate you", "There was an issue with the authentication data for the request.", HttpStatusCode.Unauthorized)

        public val ResourceNotFound: TwitterApiError = TwitterApiError(34, "Sorry, that page does not exist", "The specified resource was not found.", HttpStatusCode.NotFound)

        public val CannotReportSelf: TwitterApiError = TwitterApiError(36, "You cannot report yourself for spam.", "You cannot use your own user ID in a report spam call.", HttpStatusCode.Forbidden)

        public val ParameterMissing: TwitterApiError = TwitterApiError(38, "<named> parameter is missing.", "The request is missing the <named> parameter (such as media, text, etc.) in the request.", HttpStatusCode.Forbidden)

        public val InvalidAttachmentUrl: TwitterApiError = TwitterApiError(44, "attachment_url parameter is invalid", "The URL value provided is not a URL that can be attached to this Tweet.", HttpStatusCode.BadRequest)

        public val UserNotFound: TwitterApiError = TwitterApiError(50, "User not found.", "The user is not found.", HttpStatusCode.NotFound)

        public val SuspendedUser: TwitterApiError = TwitterApiError(63, "User has been suspended.", "The user account has been suspended and information cannot be retrieved.", HttpStatusCode.Forbidden)

        public val SuspendedAccount: TwitterApiError = TwitterApiError(64, "Your account is suspended and is not permitted to access this feature", "The access token being used belongs to a suspended user.", HttpStatusCode.Forbidden)

        public val OutdatedEndpoint: TwitterApiError = TwitterApiError(68, "The Twitter REST API v1 is no longer active. Please migrate to API v1.1.", "Corresponds to a HTTP request to a retired v1-era URL.", null)

        public val ListNameError: TwitterApiError = TwitterApiError(85, "List name error", "A list's name can't be a reserved word.", null)

        public val ActionNotPermitted: TwitterApiError = TwitterApiError(87, "Client is not permitted to perform this action.", "The endpoint called is not a permitted URL.", HttpStatusCode.Forbidden)

        public val RateLimitExceeded: TwitterApiError = TwitterApiError(88, "Rate limit exceeded", "The request limit for this resource has been reached for the current rate limit window.", null)

        public val InvalidOrExpiredToken: TwitterApiError = TwitterApiError(89, "Invalid or expired token", "The access token used in the request is incorrect or has expired.", null)

        public val SSLRequired: TwitterApiError = TwitterApiError(92, "SSL is required", "Only SSL connections are allowed in the API. Update the request to a secure connection. See how to connect using TLS", null)

        public val CannotAccessDirectMessages: TwitterApiError = TwitterApiError(93, "This application is not allowed to access or delete your direct messages", "The OAuth token does not provide access to Direct Messages.", HttpStatusCode.Forbidden)

        public val UnableToVerifyCredentials: TwitterApiError = TwitterApiError(99, "Unable to verify your credentials.", "The OAuth credentials cannot be validated. Check that the token is still valid.", HttpStatusCode.Forbidden)

        public val CannotFindSpecifiedUser: TwitterApiError = TwitterApiError(108, "Cannot find specified user", "Cannot find specified user.", null)

        public val UserNotInThisList: TwitterApiError = TwitterApiError(109, "User not in this list", "The specified user is not a member of this list.", null)

        public val AccountUpdateFailed: TwitterApiError = TwitterApiError(120, "Account update failed: value is too long (maximum is nn characters).", "Thrown when one of the values passed to the update_profile.json endpoint exceeds the maximum value currently permitted for that field. The error message will specify the allowable maximum number of nn characters.", HttpStatusCode.Forbidden)

        public val OverCapacity: TwitterApiError = TwitterApiError(130, "Over capacity", "Twitter is temporarily over capacity.", HttpStatusCode.ServiceUnavailable)

        public val InternalError: TwitterApiError = TwitterApiError(131, "Internal error", "An unknown internal error occurred.", HttpStatusCode.InternalServerError)

        public val TimestampOutOfBounds: TwitterApiError = TwitterApiError(135, "Could not authenticate you", "Timestamp out of bounds (often caused by a clock drift when authenticating - check your system clock)", HttpStatusCode.Unauthorized)

        public val AlreadyFavoritedStatus: TwitterApiError = TwitterApiError(139, "You have already favorited this status", "You have already favorited this status.", HttpStatusCode.Forbidden)

        public val NoStatusFound: TwitterApiError = TwitterApiError(144, "No status found with that ID.", "The requested Tweet ID is not found (if it existed, it was probably deleted)", HttpStatusCode.NotFound)

        public val CannotSendMessagesToUsersWhoAreNotFollowingYou: TwitterApiError = TwitterApiError(150, "You cannot send messages to users who are not following you.", "Sending a Direct Message failed.", HttpStatusCode.Forbidden)

        public val SendingMessageFailed: TwitterApiError = TwitterApiError(151, "There was an error sending your message: reason", "Sending a Direct Message failed. The reason value will provide more information.", HttpStatusCode.Forbidden)

        public val AlreadyRequestedToFollowUser: TwitterApiError = TwitterApiError(160, "You've already requested to follow user.", "This was a duplicated follow request and a previous request was not yet acknowleged.", HttpStatusCode.Forbidden)

        public val UnableToFollowAtThisTime: TwitterApiError = TwitterApiError(161, "You are unable to follow more people at this time", "Thrown when a user cannot follow another user due to some kind of limit", HttpStatusCode.Forbidden)

        public val CannotSeeProtectedStatus: TwitterApiError = TwitterApiError(179, "Sorry, you are not authorized to see this status", "Thrown when a Tweet cannot be viewed by the authenticating user, usually due to the Tweet's author having protected their Tweets.", HttpStatusCode.Forbidden)

        public val OverDailyStatusUpdateLimit: TwitterApiError = TwitterApiError(185,"User is over daily status update limit","Thrown when a Tweet cannot be posted due to the user having no allowance remaining to post. Despite the text in the error message indicating that this error is only thrown when a daily limit is reached, this error will be thrown whenever a posting limitation has been reached. Posting allowances have roaming windows of time of unspecified duration.", HttpStatusCode.Forbidden)

        public val TooLongTweet: TwitterApiError = TwitterApiError(186, "Tweet needs to be a bit shorter.", "The status text is too long.", HttpStatusCode.Forbidden)

        public val DuplicateStatus: TwitterApiError = TwitterApiError(187, "Status is a duplicate", "The status text has already been Tweeted by the authenticated account.", null)

        public val MissingOrInvalidUrl: TwitterApiError = TwitterApiError(195, "Missing or invalid url parameter", "The request needs to have a valid url parameter.", HttpStatusCode.Forbidden)

        public val OverSpamReportLimit: TwitterApiError = TwitterApiError(205, "You are over the limit for spam reports.", "The account limit for reporting spam has been reached. Try again later.", HttpStatusCode.Forbidden)

        public val MustAllowMessagesFromAnyone: TwitterApiError = TwitterApiError(214, "Owner must allow dms from anyone.", "The user is not set up to have open Direct Messages when trying to set up a welcome message.", HttpStatusCode.Forbidden)

        public val BadAuthenticationData: TwitterApiError = TwitterApiError(215, "Bad authentication data", "The method requires authentication but it was not presented or was wholly invalid.", HttpStatusCode.BadRequest)

        public val NotAllowedAccessToThisResource: TwitterApiError = TwitterApiError(220, "Your credentials do not allow access to this resource.", "The authentication token in use is restricted and cannot access the requested resource.", HttpStatusCode.Forbidden)

        public val RequestLooksLikeAutomated: TwitterApiError = TwitterApiError(226, "This request looks like it might be automated. To protect our users from spam and other malicious activity, we cannot complete this action right now.", "We constantly monitor and adjust our filters to block spam and malicious activity on the Twitter platform. These systems are tuned in real-time. If you get this response our systems have flagged the Tweet or Direct Message as possibly fitting this profile. If you believe that the Tweet or DM you attempted to create was flagged in error, report the details by filing a ticket at https://help.twitter.com/forms/platform.", null)

        public val UserMustVerifyLogin: TwitterApiError = TwitterApiError(231, "User must verify login", "Returned as a challenge in xAuth when the user has login verification enabled on their account and needs to be directed to twitter.com to generate a temporary password. Note that xAuth is no longer an available option for authentication on the API.", null)

        public val RetiredEndpoint: TwitterApiError = TwitterApiError(251, "This endpoint has been retired and should not be used.", "Corresponds to a HTTP request to a retired URL.", null)

        public val CannotPerformWriteActions: TwitterApiError = TwitterApiError(261, "Application cannot perform write actions.", "Thrown when the application is restricted from POST, PUT, or DELETE actions. Check the information on your application dashboard. You may also file a ticket atÂ https://help.twitter.com/forms/platform.", HttpStatusCode.Forbidden)

        public val CannotMuteYourself: TwitterApiError = TwitterApiError(271, "You can't mute yourself.", "The authenticated user account cannot mute itself.", HttpStatusCode.Forbidden)

        public val NotMutingTheSpecifiedUser: TwitterApiError = TwitterApiError(272, "You are not muting the specified user.", "The authenticated user account is not muting the account a call is attempting to unmute.", HttpStatusCode.Forbidden)

        public val NotAllowedAnimatedGIFWhenUploadingMultipleImages: TwitterApiError = TwitterApiError(323, "Animated GIFs are not allowed when uploading multiple images.", "Only one animated GIF may be attached to a single Tweet.", HttpStatusCode.BadRequest)

        public val MediaIdValidationFailed: TwitterApiError = TwitterApiError(324, "The validation of media ids failed.", "There was a problem with the media ID submitted with the Tweet.", HttpStatusCode.BadRequest)

        public val MediaIdNotFound: TwitterApiError = TwitterApiError(325, "A media id was not found.", "The media ID attached to the Tweet was not found.", HttpStatusCode.BadRequest)

        public val TemporarilyLockedAccount: TwitterApiError = TwitterApiError(326, "To protect our users from spam and other malicious activity, this account is temporarily locked.", "The user should log in to https://twitter.com to unlock their account before the user token can be used.", HttpStatusCode.Forbidden)

        public val AlreadyRetweetedTweet: TwitterApiError = TwitterApiError(327, "You have already retweeted this Tweet.", "The user cannot retweet the same Tweet more than once.", HttpStatusCode.Forbidden)

        public val CannotSendMessagesToThisUser: TwitterApiError = TwitterApiError(349, "You cannot send messages to this user.", "The sender does not have privileges to Direct Message the recipient.", HttpStatusCode.Forbidden)

        public val TooLongDirectMessage: TwitterApiError = TwitterApiError(354, "The text of your direct message is over the max character limit.", "The message size exceeds the number of characters permitted in a Direct Message.", HttpStatusCode.Forbidden)

        public val AlreadySubscribed: TwitterApiError = TwitterApiError(355, "Subscription already exists.", "Related to Account Activity API request to add a new subscription for an authenticated user.", HttpStatusCode.Conflict)

        public val ReplyToDeletedOrInvisibleTweet: TwitterApiError = TwitterApiError(385, "You attempted to reply to a Tweet that is deleted or not visible to you.", "A reply can only be sent with reference to an existing public Tweet.", HttpStatusCode.Forbidden)

        public val TooManyAttachments: TwitterApiError = TwitterApiError(386, "The Tweet exceeds the number of allowed attachment types.", "A Tweet is limited to a single attachment resource (media, Quote Tweet, etc.)", HttpStatusCode.Forbidden)

        public val InvalidURL: TwitterApiError = TwitterApiError(407, "The given URL is invalid.", "A URL included in the Tweet could not be handled. This may be because a non-ASCII URL could not be converted, or for other reasons.", HttpStatusCode.Forbidden)

        public val UnapprovedCallbackURL: TwitterApiError = TwitterApiError(415, "Callback URL not approved for this client application. Approved callback URLs can be adjusted in your application settings", "The app callback URLs must be whitelisted via the app details page in the developer portal. Only approved callback URLs may be used by the Twitter app. See the Callback URL documentation.", HttpStatusCode.Forbidden)

        public val InvalidOrSuspendedApplication: TwitterApiError = TwitterApiError(416, "Invalid / suspended application", "The app has been suspended and cannot be used with Sign-in with Twitter.", HttpStatusCode.Unauthorized)

        public val InvalidOAuthCallback: TwitterApiError = TwitterApiError(417, "Desktop applications only support the oauth_callback value 'oob'", "The application is attempting to use out-of-band PIN-based OAuth, but a callback URL has been specified in the app settings.", HttpStatusCode.Unauthorized)
    }
}

private val TwitterApiError.Companion.errors: List<TwitterApiError>
    get() = listOf(
        InvalidCoordinates, NoLocationAssociated, NoUserMatches, CouldNotAuthenticate, ResourceNotFound,
        CannotReportSelf, ParameterMissing, InvalidAttachmentUrl, UserNotFound, SuspendedUser, SuspendedAccount,
        OutdatedEndpoint, ListNameError, ActionNotPermitted, RateLimitExceeded, InvalidOrExpiredToken, SSLRequired,
        CannotAccessDirectMessages, UnableToVerifyCredentials, CannotFindSpecifiedUser, UserNotInThisList,
        AccountUpdateFailed, OverCapacity, InternalError, TimestampOutOfBounds, AlreadyFavoritedStatus,
        NoStatusFound, CannotSendMessagesToUsersWhoAreNotFollowingYou, SendingMessageFailed, AlreadyRequestedToFollowUser,
        UnableToFollowAtThisTime, CannotSeeProtectedStatus, OverDailyStatusUpdateLimit, TooLongTweet, DuplicateStatus,
        MissingOrInvalidUrl, OverSpamReportLimit, MustAllowMessagesFromAnyone, BadAuthenticationData,
        NotAllowedAccessToThisResource, RequestLooksLikeAutomated, UserMustVerifyLogin, RetiredEndpoint,
        CannotPerformWriteActions, CannotMuteYourself, NotMutingTheSpecifiedUser, NotAllowedAnimatedGIFWhenUploadingMultipleImages,
        MediaIdValidationFailed, MediaIdNotFound, TemporarilyLockedAccount, AlreadyRetweetedTweet,
        CannotSendMessagesToThisUser, TooLongDirectMessage, AlreadySubscribed, ReplyToDeletedOrInvisibleTweet,
        TooManyAttachments, InvalidURL, UnapprovedCallbackURL, InvalidOrSuspendedApplication, InvalidOAuthCallback
    )

internal fun throwApiError(code: Int?, message: String, content: String, request: HttpRequest, response: HttpResponse): Nothing {
    val error = code?.let { TwitterApiError.errors.find { it.code == code } }
        ?: throw PenicillinException(LocalizedString.UnknownApiError, null, request, response, code, message, content)

    throw PenicillinTwitterApiException(error, request, response)
}
