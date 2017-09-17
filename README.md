[![Kotlin 1.1.4](https://img.shields.io/badge/Kotlin-1.1.4-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)
[![Travis](https://img.shields.io/travis/NephyProject/Penicillin.svg)](https://travis-ci.org/NephyProject/Penicillin/builds)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)

Penicillin: *Twitter* *API* *wrapper* for *Kotlin*
===========================

- Supports not only OAuth 1.0a but Application-only authentication (OAuth 2.0).
- Supports all public Twitter API except Beta API such as Webhooks API.
- Supports some private Twitter API such as *Polling* *Tweets*.
- Because all available endpoint parameters are named arguments of the method, it is easy to use APIs.
- Since all response models are defined, it's possible by IDE to complement response fields.

Install
-------
Current version is `v1.0.1`. Show all from [Change Logs](https://github.com/NephyProject/Penicillin/blob/master/CHANGELOG.md).

Gradle:
```groovy
dependencies {
    compile "jp.nephy:penicillin:1.0.1"
}
```

Maven:
```xml
<dependency>
    <groupId>jp.nephy</groupId>
    <artifactId>penicillin</artifactId>
    <version>1.0.1</version>
</dependency>
```

Release: [Latest](https://github.com/NephyProject/Penicillin/releases/latest) from GitHub

Usage
=====

Authentication
-------------
OAuth 1.0a (Basic case)
```kotlin
import jp.nephy.penicillin.Client
import jp.nephy.penicillin.credentials.*

val api = Client.authenticate(
    ck = ConsumerKey("XXX"),
    cs = ConsumerSecret("YYY"),
    at = AccessToken("xxxxx-yyyyy"),
    ats = AccessTokenSecret("zzzzz")
)
```

Application-only authentication
```kotlin
import jp.nephy.penicillin.Client
import jp.nephy.penicillin.credentials.*

val api = Client.authenticate(
    token = BearerToken("XXX")
)
```

Of course, you may issue Access Token/Token Secret via Request Token/Token Secret.
```kotlin
val api = Client.authenticate(
    ck = ConsumerKey("XXX"),
    cs = ConsumerSecret("YYY")
)
val (at, ats) = api.oauth.getAccessTokenCLI()
```

Further, this is example for OAuth 2.0 to issue Bearer Token.
```kotlin
val api = Client.authenticate(
    ck = ConsumerKey("XXX"),
    cs = ConsumerSecret("YYY")
)
val token = api.oauth2.getBearerToken()
```

API Examples
-------------
Getting favorites from @Twitter.
```kotlin
val responseList = api.favorite.getList(screenName = "Twitter")
responseList.forEach {
    println("${it.user.name} @${it.user.screenName}\n${it.text}")
    // XXX @YYY
    // TWEET TEXT
}
```


Posting a tweet with media.
```kotlin
import jp.nephy.penicillin.parameters.MediaType
import java.io.File

api.status.updateWithMediaFile(
    status = "this is a media tweet.",
    media = arrayOf(File("/path/to/file.png") to MediaType.PNG)
)
```


Posting a poll tweet. **This requires Twitter Official CK/CS such as `Twitter for iPhone`.**
```kotlin
api.status.createPollTweet(
    status="Which SNS do you love the best?",
    choices = arrayOf("Twitter", "Facebook", "Mastodon", "Weibo"),
    minutes = 60 * 24 * 3
)
```


Connecting to UserStream then print all the stream.
```kotlin
val responseStream = api.stream.getUserStream(includeFollowingsActivity = true)
val listener = responseStream.listen(PrintUserStreamListener())

// process stream asynchronously (non-blocking)

Thread.sleep(10000) // stop streaming after 10s
listener.terminate()
```
try implementing a custom UserStream listener by inheriting `IUserStreamListener` then pass its instance to `ResponseStream#listen`.


TODO
-------
- Cursor support.
- Adding plenty of Twitter undocumented/private APIs.
- Documentation.


Thanks
---------
Penicillin uses below 3rd party libraries.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.
- Kotson (https://github.com/SalomonBrys/Kotson) by [@SalomonBrys](https://github.com/SalomonBrys)
- nv-i18n (https://github.com/TakahikoKawasaki/nv-i18n) by [@TakahikoKawasaki](https://github.com/TakahikoKawasaki)


License
---------
Penicillin is provided under MIT License.  

Copyright (c) 2017 Nephy Project
