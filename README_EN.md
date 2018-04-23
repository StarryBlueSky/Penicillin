[![Kotlin 1.2.0](https://img.shields.io/badge/Kotlin-1.2.21-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)
[![Travis](https://img.shields.io/travis/NephyProject/Penicillin.svg)](https://travis-ci.org/NephyProject/Penicillin/builds)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)

Penicillin: *Twitter* *API* *wrapper* for *Kotlin*
===========================

- Supports all public Twitter Apis and Authentication methods.
- Supports some private Twitter Apis such as *Polling* *Tweets*.
- Because all available endpoint parameters are named arguments of the method, it is easy to use APIs.
- Since all response models are defined, it's possible by IDE to complement response fields.
- Supports cursoring. In endpoints that uses `cursor`, you can use `.next()`, `.untilLast()` and so on.

If you found a new Twitter API endpoint, Please tell us at [Issues](https://github.com/NephyProject/Penicillin/issues). We'll investigate it soon.

Install
-------
Current version is [![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22).  
Show all from [Change Logs](https://github.com/NephyProject/Penicillin/blob/master/CHANGELOG.md).

Gradle:
```groovy
compile "jp.nephy:penicillin:{penicillin_version}"
```

Maven:
```xml
<dependency>
    <groupId>jp.nephy</groupId>
    <artifactId>penicillin</artifactId>
    <version>{penicillin-version}</version>
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
val client = PenicillinClient.build {
    // ConsumerKey = XXXXX, ConsumerSecret = YYYYY
    application("XXXXX", "YYYYY")
    // application(OfficialClient.TwitterForiPhone)
    
    // AccessToken = xxxxx-xxxxx, AccessTokenSecret = yyyyy
    token("xxxxx-xxxxx", "yyyyy")
    // BearerToken = ZZZZZ
    token("ZZZZZ")
    
    httpClient { // this = OkhttpHttpClient.Builder
        connectTimeout(20, TimeUnit.SECONDS)
        readTimeout(40, TimeUnit.SECONDS)
        writeTimeout(20, TimeUnit.SECONDS)
    }
}
```

API Examples
-------------
Getting favorites from @Twitter.
```kotlin
val response = client.favorite.list(screenName = "Twitter")
response.forEach {
    println("${it.user.name} @${it.user.screenName}\n${it.fullText}")
    // XXX @YYY
    // TWEET TEXT
}
```


Retrieve the entire follower list. (Cursoring sample)
```kotlin
val firstResponse = client.follower.list()
val secondResponse = firstResponse.next()  // next cursor
secondResponse.previous()  // this is same as firstResponse

firstResponse.untilLast().forEach {
    // Instead of this, use .all() to acquire including firstResponse.
    it.result.users.forEach {
        println(it.screenName)
    }
}
```


Posting a tweet with media.
```kotlin
client.status.updateWithMediaFile(
    status = "this is a media tweet.",
    media = listOf(File("/path/to/file.png") to MediaType.PNG)
)
```


Posting a poll tweet.
```kotlin
client.status.createPollTweet(
    status = "Which SNS do you love the best?",
    choices = listOf("Twitter", "Facebook", "Mastodon", "Weibo"),
    minutes = 60 * 24 * 5 // 5 days
)
```


Connecting to UserStream then print all the stream.
```kotlin
val responseStream = client.stream.userStream(includeFollowingsActivity = true)
val listener = responseStream.listen(object: UserStreamListener {
    override fun onStatus(status: Status) {
        println(status.fullText) // Prints all tweets.
    }
}).onClose { println("Stdout: Closed.") }.start(wait = false)

// process stream asynchronously (non-blocking)

Thread.sleep(10000) // stop streaming after 10s
listener.terminate() // Stdout: Closed.
```


TODO
-------
- Documentation.


Thanks
---------
Penicillin uses below 3rd party libraries.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.


License
---------
Penicillin is provided under MIT License.  

Copyright (c) 2018 Nephy Project.
