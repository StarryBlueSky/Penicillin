package jp.nephy.penicillin.core.emulation

import io.ktor.http.Headers
import jp.nephy.penicillin.core.session.Session

class Tweetdeck(override val session: Session): Emulation() {
    override val headers = Headers.build {
        append("accept", "text/plain, */*; q=0.01")
        append("accept-encoding", "gzip, deflate, br")
        append("accept-language", "ja")
        append("dnt", "1")
        append("origin", "https://tweetdeck.twitter.com")
        append("referer", "https://tweetdeck.twitter.com/")
        append("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36")
        append("x-csrf-token", "")
        append("x-twitter-auth-type", "OAuth2Session")
        append("x-twitter-client-version", "Twitter-TweetDeck-blackbird-chrome/4.0.180829111928 web/")
    }
}
