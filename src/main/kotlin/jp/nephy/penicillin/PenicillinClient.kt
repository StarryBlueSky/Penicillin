package jp.nephy.penicillin

import jp.nephy.penicillin.core.session.SessionBuilder
import jp.nephy.penicillin.endpoints.*
import jp.nephy.penicillin.endpoints.Collection
import java.io.Closeable

@Suppress("UNUSED")
class PenicillinClient(session: SessionBuilder.() -> Unit = {}): Closeable {
    val session = SessionBuilder().apply(session).build()
    val account = Account(this)
    val accountActivity = AccountActivity(this)
    val activity = Activity(this)
    val application = Application(this)
    val block = Block(this)
    val card = Card(this)
    val collection = Collection(this)
    val collectionEntry = CollectionEntry(this)
    val directMessage = DirectMessage(this)
    val directMessageEvent = DirectMessageEvent(this)
    val favorite = Favorite(this)
    val follower = Follower(this)
    val followRequest = FollowRequest(this)
    val friend = Friend(this)
    val friendship = Friendship(this)
    val geo = Geo(this)
    val help = Help(this)
    val livePipeline = LivePipeline(this)
    val list = Lists(this)
    val media = Media(this)
    val misc = Misc(this)
    val moment = Moment(this)
    val mute = Mute(this)
    val notifications = Notifications(this)
    val oauth = OAuth(this)
    val oauth2 = OAuth2(this)
    val savedSearch = SavedSearch(this)
    val search = Search(this)
    val status = Status(this)
    val stream = Stream(this)
    val timeline = Timeline(this)
    val trend = Trend(this)
    val user = User(this)
    val welcomeMessage = WelcomeMessage(this)
    val welcomeMessageRule = WelcomeMessageRule(this)

    override fun close() {
        session.close()
    }
}
