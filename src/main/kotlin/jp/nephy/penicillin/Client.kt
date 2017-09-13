package jp.nephy.penicillin

import jp.nephy.penicillin.credential.*
import jp.nephy.penicillin.endpoint.*
import jp.nephy.penicillin.endpoint.Collection
import jp.nephy.penicillin.endpoint.List
import jp.nephy.penicillin.misc.AuthorizationType

class Client(ck: ConsumerKey?, cs: ConsumerSecret?, at: AccessToken?, ats: AccessTokenSecret?, token: BearerToken?) {
    companion object {
        fun authenticate(ck: ConsumerKey?=null, cs: ConsumerSecret?=null, at: AccessToken?=null, ats: AccessTokenSecret?=null, token: BearerToken?=null) = Client(ck, cs, at, ats, token)
    }

    val session = Session(this)
    var authType = AuthorizationType.OAuth1a

    init {
        authType = if (ck != null && cs != null && at != null && ats != null) {
            session.authenticate(ck, cs, at, ats)
            AuthorizationType.OAuth1a
        } else if (ck != null && cs != null) {
            session.authenticate(ck, cs)
            AuthorizationType.OAuth1aRequestToken
        } else if (token != null) {
            session.authenticate(token)
            AuthorizationType.OAuth2
        } else {
            throw IllegalArgumentException("ck, cs, at, ats, token are all null.")
        }
    }

    fun authType(type: AuthorizationType) = this.apply {
        authType = type
    }

    val account = Account(this)
    val application = Application(this)
    val block = Block(this)
    val card = Card(this)
    val collection = Collection(this)
    val dm = DirectMessage(this)
    val favorite = Favorite(this)
    val follower = Follower(this)
    val friend = Friend(this)
    val friendship = Friendship(this)
    val geo = Geo(this)
    val help = Help(this)
    val list = List(this)
    val media = Media(this)
    val mute = Mute(this)
    val oauth = OAuth(this)
    val oauth2 = OAuth2(this)
    val savedSearch = SavedSearch(this)
    val status = Status(this)
    val stream = Stream(this)
    val trend = Trend(this)
    val user = User(this)
}
