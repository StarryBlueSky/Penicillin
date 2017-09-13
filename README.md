# Penicillin
Simple Twitter API wrapper especially supporting Undocumented Twitter API.

## TODO
- Basic / Bearer / App-Only-Auth supports.
- Cursor endpoint supports.
- Arrangement of data classes.
- Adding plenty of Twitter undocumented/private APIs.
- Thoroughness of Null safety (sometimes data classes break)
- Webhooks API supports. (after announce from official)
- Prepare API documents. (maybe GitHub Wiki?)
- shallow delegate (e.g. val obj by json.byObjectA -> val attributeA by json["A"].byString)

## How to use
just simply authenticate
```kotlin
val api = Client.authenticate(
    ck = ConsumerKey("XXX"),
    cs = ConsumerSecret("YYY"),
    at = AccessToken("xxxxx-yyyyy"),
    ats = AccessTokenSecret("zzzzz")
)
```


Sample 1: Getting favorites list of @Twitter.
```kotlin
val responseList = api.favorite.getList(screenName="Twitter")
responseList.forEach {
    println("${it.user.name} @${it.user.screenName}\n${it.text}")
    // XXX @YYY
    // TWEET TEXT
}
```

Sample 2: Connection to UserStream then print all the stream.
```kotlin
val responseStream = api.stream.getUserStream(includeFollowingsActivity = true)
val listener = responseStream.listen(PrintUserStreamListener())

// process stream asynchronously (non-blocking)

Thread.sleep(10000) // stop streaming after 10s
listener.terminate()
```
try implementing a custom UserStream listener by inheriting `IUserStreamListener` then pass it to `ResponseStream#listen`.

## Thanks
Penicillin uses below 3rd party libraries.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.
- Kotson (https://github.com/SalomonBrys/Kotson) by [@SalomonBrys](https://github.com/SalomonBrys)
- nv-i18n (https://github.com/TakahikoKawasaki/nv-i18n) by [@TakahikoKawasaki](https://github.com/TakahikoKawasaki)

## License
Penicillin is provided under MIT License.  

Copyright (c) 2017 Nephy Project
