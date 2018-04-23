package jp.nephy.penicillin

import jp.nephy.penicillin.endpoint.*
import jp.nephy.penicillin.endpoint.Collection
import okhttp3.OkHttpClient


class PenicillinClient private constructor(val session: Session) {
    companion object {
        fun build(operation: Builder.() -> Unit) = Builder().apply(operation).build()
    }

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
    val oauth = OAuth(this)
    val oauth2 = OAuth2(this)
    val savedSearch = SavedSearch(this)
    val status = Status(this)
    val stream = Stream(this)
    val timeline = Timeline(this)
    val trend = Trend(this)
    val user = User(this)
    val welcomeMessage = WelcomeMessage(this)
    val welcomeMessageRule = WelcomeMessageRule(this)

    class Builder {
        private var isOfficialClient = false
        private var consumerKey: String? = null
        private var consumerSecret: String? = null
        fun application(client: OfficialClient) {
            isOfficialClient = true
            consumerKey = client.consumerKey
            consumerSecret = client.consumerSecret
        }

        fun application(consumerKey: String, consumerSecret: String) {
            this.consumerKey = consumerKey
            this.consumerSecret = consumerSecret
            OfficialClient.values().find { it.consumerKey == consumerKey && it.consumerSecret == consumerSecret }
                    ?: return
            isOfficialClient = true
        }

        private var accessToken: String? = null
        private var accessTokenSecret: String? = null
        private var bearerToken: String? = null
        fun token(accessToken: String, accessTokenSecret: String) {
            this.accessToken = accessToken
            this.accessTokenSecret = accessTokenSecret
        }

        fun token(bearerToken: String) {
            this.bearerToken = bearerToken
        }

        private var httpClientBuilder: OkHttpClient.Builder.() -> Unit = {  }
        fun httpClient(builder: OkHttpClient.Builder.() -> Unit) {
            httpClientBuilder = builder
        }

        private var maxRetries = 3
        fun maxRetries(count: Int) {
            maxRetries = count
        }

        fun build(): PenicillinClient {
            val session = Session(consumerKey, consumerSecret, accessToken, accessTokenSecret, isOfficialClient, bearerToken, httpClientBuilder, maxRetries)

            return PenicillinClient(session)
        }
    }
}
