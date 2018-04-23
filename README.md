[![Kotlin 1.2.40](https://img.shields.io/badge/Kotlin-1.2.40-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)
[![Travis](https://img.shields.io/travis/NephyProject/Penicillin.svg)](https://travis-ci.org/NephyProject/Penicillin/builds)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)

English README is [here](https://github.com/NephyProject/Penicillin/blob/master/README_EN.md).  


Penicillin: *Kotlin*のためのTwitter API ラッパー
===========================

- すべての公開Twitter APIと認証方式(OAuth 1.0a, OAuth 2.0)に対応しています.
- 一部の非公開APIに対応しています(投票など). 今後もサポートを充実する予定です.
- 全エンドポイントのパラメータがKotlinの名前付き引数として解決できるため, 簡単にAPIが利用できます.
- 全エンドポイントのレスポンスのモデルクラスが用意されているので, APIの返り値の利用が容易にできます.
- 非同期なAPI実行に対応しています.
- カーソル操作があるエンドポイントでは`.next()`や`.untilLast()` といったメソッドでページングできます.

もし未対応のエンドポイントやバグを発見した際は, お気軽に [Issue](https://github.com/NephyProject/Penicillin/issues) を立ててください. すぐに対応します.

導入
-------
現在のバージョンは [![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22) です.  
バージョン履歴は [CHANGELOG.md](https://github.com/NephyProject/Penicillin/blob/master/CHANGELOG.md) に記載しています.

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

Release: リリース版のJarは [GitHub](https://github.com/NephyProject/Penicillin/releases) から利用可能です.  
Snapshot: 開発版のJarは [Sonatype Snapshots Repository](https://oss.sonatype.org/content/repositories/snapshots/jp/nephy/penicillin/) から利用可能です.

使用例
=====

ユーザ認証
-------------
```kotlin
val client = PenicillinClient.build {
    // ConsumerKey = XXXXX, ConsumerSecret = YYYYY
    application("XXXXX", "YYYYY")
    // 公式クライアントのConsumerKey, ConsumerSecretは定義済みです
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

API実行
-------------
@Twitterのお気に入りを表示する. (現在のスレッドで実行; ブロッキング)
```kotlin
val api = try {
    client.favorite.list(screenName = "Twitter").complete()
} catch (e: PenicillinException) {
    logger.error(e) { "APIリクエスト中にエラーが発生しました." }
    return
}

api.result.forEach {
    println("${it.user.name} @${it.user.screenName}\n${it.fullText}")
    // XXX @YYY
    // TWEET TEXT
}
```


ホームタイムラインを表示する. (非同期で実行)
```kotlin
client.timeline.home().queue {
    it.result.forEach {
        println("${it.user.name} @${it.user.screenName}\n${it.fullText}")
        // XXX @YYY
        // TWEET TEXT
    }
}
```


フォロワー一覧を全件取得する. (カーソル操作)
```kotlin
val firstResponse = client.follower.list().complete()
val secondResponse = firstResponse.next()  // next cursor
secondResponse.previous()  // this is same as firstResponse

firstResponse.untilLast().forEach {
    it.result.users.forEach {
        println(it.screenName)
    }
}
```


画像付きツイートを投稿する.
```kotlin
client.status.updateWithMediaFile(
    status = "this is a media tweet.",
    media = listOf(File("/path/to/file.png") to MediaType.PNG)
).complete()
```


投票ツイートを投稿する. (公式クライアントの認証情報が必要です！)
```kotlin
client.status.createPollTweet(
    status = "どのSNSが一番好き？",
    choices = listOf("Twitter", "Facebook", "Mastodon", "Weibo"),
    minutes = 60 * 24 * 5 // 5 days
)
```


UserStreamに接続し, ツイートを表示する.
```kotlin
val api = client.stream.userStream()
val listener = api.listen(object: UserStreamListener {
    override fun onConnect() {
        println("UserStreamに接続しました.")
    }
    override fun onDisconnect() {
        println("UserStreamから切断されました.")
    }

    override fun onStatus(status: Status) {
        println(status.fullText)
    }
}).start(wait = false)

// wait = falseで非同期でストリームを処理します

Thread.sleep(10000)
listener.terminate() // UserStreamを切断
```


TODO
-------
- ドキュメントを整備する.


謝辞
---------
Penicillinは以下のサードパーティライブラリを利用しています.
- OkHttp3 (https://github.com/square/okhttp) by Square, Inc.
- Gson (https://github.com/google/gson) by Google, Inc.


ライセンス
---------
PenicillinはMITライセンスで提供されています. Nephy ProjectにおけるMITライセンスは [こちら](https://nephy.jp/license/mit) をご覧ください.

Copyright (c) 2018 Nephy Project.
