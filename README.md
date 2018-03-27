[![Kotlin 1.2.0](https://img.shields.io/badge/Kotlin-1.2.21-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)
[![Travis](https://img.shields.io/travis/NephyProject/Penicillin.svg)](https://travis-ci.org/NephyProject/Penicillin/builds)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)

Penicillin: *Twitter* *API* *wrapper* for *Kotlin*
===========================

- Protocol support for HTTP/1.1 & HTTP/2.
- Supports not only OAuth 1.0a but Application-only authentication (OAuth 2.0).
- Supports all public Twitter API except Beta API such as Webhooks API.
- Supports some private Twitter API such as *Polling* *Tweets*.
- Because all available endpoint parameters are named arguments of the method, it is easy to use APIs.
- Since all response models are defined, it's possible by IDE to complement response fields.
- Supports cursoring. In endpoints that uses `cursor`, you can use `.next()`, `.all()` and so on.

If you found a new Twitter API endpoint, Please tell us at [Issues](https://github.com/NephyProject/Penicillin/issues). We'll investigate it soon.

Install
-------
Current version is `v1.4.1`. Show all from [Change Logs](https://github.com/NephyProject/Penicillin/blob/master/CHANGELOG.md).

Gradle:
```groovy
compile "jp.nephy:penicillin:1.4.1"
```

Maven:
```xml
<dependency>
    <groupId>jp.nephy</groupId>
    <artifactId>penicillin</artifactId>
    <version>1.4.1</version>
</dependency>
```

Release: releases are available in [GitHub](https://github.com/NephyProject/Penicillin/releases).  
Snapshot: development versions are available in [Sonatype Snapshots Repository](https://oss.sonatype.org/content/repositories/snapshots/jp/nephy/penicillin/).

Usage
=====

Authentication
-------------
Setup a Client. (basic use)
```kotlin
import jp.nephy.penicillin.Client
import jp.nephy.penicillin.credentials.*

val client = Client.builder()
    .authenticate(
        ConsumerKey("XXXXX"), ConsumerSecret("YYYYY"),
        AccessToken("xxxxx-xxxxx"), AccessTokenSecret("yyyyy")
    )
    .authenticate(BearerToken("ZZZZZ"))  // optional: if you have a Bearer Token
    .connectTimeout(20)  // optional: timeouts in sec
    .readTimeout(40)
    .writeTimeout(20)
    .build()  // return Client instance
```

Of course, you may issue Access Token/Token Secret via Request Token/Token Secret or issue Bearer Token.
```kotlin
val client = Client.builder()
    .authenticate(
        ConsumerKey("XXX"), ConsumerSecret("YYY")
    )
    .build()
val (at, ats) = client.oauth.getAccessTokenCLI()
val token = client.oauth2.getBearerToken()
```

Further, if you'd like to access Private API, try to use `.officialClient` instead of `.authenticate`.
```kotlin
val client = Client.builder()
    .officialClient(
        OfficialClient.Twitter_for_iPhone,
        AccessToken("xxxxx-xxxxx"), AccessTokenSecret("yyyyy")
    )
    .build()
```

API Examples
-------------
Getting favorites from @Twitter.
```kotlin
val responseList = client.favorite.getList(screenName = "Twitter")
responseList.forEach {
    println("${it.user.name} @${it.user.screenName}\n${it.text}")
    // XXX @YYY
    // TWEET TEXT
}
```


Retrieve the entire follower list. (Cursoring sample)
```kotlin
val firstResponse = client.follower.getList()
val secondResponse = firstResponse.next()  // next cursor
secondResponse.previous()  // this is same as firstResponse

firstResponse.untilLast().forEach {
    // .untilLast() does NOT contain firstResponse.
    // Instead of this, use .all() to acquire including firstResponse.
    it.result.users.forEach {
        println(it.screenName)
    }
}
```


Posting a tweet with media.
```kotlin
import jp.nephy.penicillin.parameters.MediaType
import java.io.File

client.status.updateWithMediaFile(
    status = "this is a media tweet.",
    media = arrayOf(File("/path/to/file.png") to MediaType.PNG)
)
```


Posting a poll tweet. **This requires auth by `.officialClient`.**
```kotlin
client.status.createPollTweet(
    status="Which SNS do you love the best?",
    choices = arrayOf("Twitter", "Facebook", "Mastodon", "Weibo"),
    minutes = 60 * 24 * 3
)
```


Connecting to UserStream then print all the stream.
```kotlin
import jp.nephy.penicillin.streaming.PrintUserStreamListener

val responseStream = client.stream.getUserStream(includeFollowingsActivity = true)
val listener = responseStream.listen(PrintUserStreamListener())
        .onClose { println("Stdout: Closed.") }
        .start()
// process stream asynchronously (non-blocking)

Thread.sleep(10000) // stop streaming after 10s
listener.terminate() // Stdout: Closed.
```
try implementing a custom UserStream listener by inheriting `IUserStreamListener` then pass its instance to `ResponseStream#listen`.


TODO
-------
- [In progress] Adding plenty of Twitter undocumented/private APIs.
- Documentation.


Thanks
---------
Penicillin uses below 3rd party libraries.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.
- nv-i18n (https://github.com/TakahikoKawasaki/nv-i18n) by [@TakahikoKawasaki](https://github.com/TakahikoKawasaki)


License
---------
Penicillin is provided under MIT License.  

Copyright (c) 2018 Nephy Project.
