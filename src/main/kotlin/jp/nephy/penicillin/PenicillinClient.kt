package jp.nephy.penicillin

import jp.nephy.penicillin.core.session.SessionBuilder
import jp.nephy.penicillin.endpoints.*
import kotlinx.io.core.Closeable

@Suppress("UNUSED")
class PenicillinClient(session: SessionBuilder.() -> Unit): Closeable {
    val session = SessionBuilder().apply(session).build()

    val account = Account(this)
    val accountActivity = AccountActivity(this)
    val activity = Activity(this)
    val application = Application(this)
    val blocks = Blocks(this)
    val cards = Cards(this)
    val collections = Collections(this)
    val collectionEntries = CollectionEntries(this)
    val directMessages = DirectMessages(this)
    val directMessageEvent = DirectMessageEvents(this)
    val favorites = Favorites(this)
    val followers = Followers(this)
    val followRequests = FollowRequests(this)
    val friends = Friends(this)
    val friendships = Friendships(this)
    val geo = Geo(this)
    val help = Help(this)
    val livePipeline = LivePipeline(this)
    val lists = Lists(this)
    val media = Media(this)
    val misc = Misc(this)
    val moments = Moments(this)
    val mutes = Mutes(this)
    val notifications = Notifications(this)
    val oauth = OAuth(this)
    val oauth2 = OAuth2(this)
    val savedSearches = SavedSearches(this)
    val search = Search(this)
    val statuses = Statuses(this)
    val stream = Stream(this)
    val timeline = Timeline(this)
    val trends = Trends(this)
    val users = Users(this)
    val welcomeMessages = WelcomeMessages(this)
    val welcomeMessageRules = WelcomeMessageRules(this)

    override fun close() {
        session.close()
    }
}
