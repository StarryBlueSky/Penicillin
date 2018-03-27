package jp.nephy.penicillin

import jp.nephy.penicillin.auth.AuthorizationType
import jp.nephy.penicillin.endpoint.*
import jp.nephy.penicillin.endpoint.Collection
import jp.nephy.penicillin.endpoint.List


class Client(val session: Session) {
    companion object {
        fun builder() = ClientBuilder()
    }

    fun authType(type: AuthorizationType) = this.apply {
        session.authType = type
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
    val misc = Misc(this)
    val mute = Mute(this)
    val oauth = OAuth(this)
    val oauth2 = OAuth2(this)
    val savedSearch = SavedSearch(this)
    val status = Status(this)
    val stream = Stream(this)
    val trend = Trend(this)
    val user = User(this)
}
