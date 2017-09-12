# Penicillin
Simple Twitter API wrapper especially supporting Undocumented Twitter API.

## TODO
- Basic / Bearer / App-Only-Auth supports.
- Cursor endpoint supports.
- Arrangement of data classes.
- Streaming API handling.
- Adding plenty of Twitter undocumented/private APIs.
- Thoroughness of Null safety (sometimes data classes break)
- Webhooks API supports. (after announce from official)
- Prepare API documents. (maybe GitHub Wiki?)

## How to use
```kotlin
import jp.nephy.penicillin.Client
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

## Thanks
Penicillin uses below 3rd party libraries.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.
- Kotson (https://github.com/SalomonBrys/Kotson) by [@SalomonBrys](https://github.com/SalomonBrys)
- nv-i18n (https://github.com/TakahikoKawasaki/nv-i18n) by [@TakahikoKawasaki](https://github.com/TakahikoKawasaki)

## License
Penicillin is provided under MIT License.  

Copyright (c) 2017 Nephy Project
