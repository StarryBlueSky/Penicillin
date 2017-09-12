# Penicillin
Simple Twitter API wrapper especially supporting Undocumented Twitter API.

## Developing State
- Currently supports `Twitter for iPhone`, `Twitter for Android` and so on. (Only Official Client CK/CS)
- Supports Polling Tweet (Currently only create. In the future, supports voting.)

## How to use
```kotlin
import jp.nephy.penicillin
import jp.nephy.penicillin.credential.*

fun main(args: Array<String>) {
    val api = Client.authenticate(
        ck = ConsumerKey("XXX"),
        cs = ConsumerSecret("YYY"),
        at = AccessToken("xxxxx-yyyyy"),
        ats = AccessTokenSecret("zzzzz")
    )
    
    val result = api.favorite.getList() // call `GET /1.1/favorites/list.json`
    result.forEach {
        println("${it.user.name} @${it.user.screenName}\n${it.text}")
        // XXX @YYY
        // ........
    }
}
```