# Penicillin: Full-featured Twitter API wrapper for Kotlin

[![Kotlin 1.3.11](https://img.shields.io/badge/Kotlin-1.3.11-blue.svg)](http://kotlinlang.org)
[![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)
[![Travis](https://img.shields.io/travis/NephyProject/Penicillin.svg)](https://travis-ci.org/NephyProject/Penicillin/builds)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/pulls)

* Enterprise API を除く, すべての公開 Twitter API と認証方式 (OAuth 1.0a, OAuth 2.0) に対応しています。
* 一部の非公開 API に対応しています(投票など)。今後もサポートを充実する予定です。
* エンドポイントのパラメータが名前付き引数として解決できるため, 簡単に API が利用できます。
* エンドポイントのレスポンスのモデルクラスが用意されているので, API の返り値の利用が容易にできます。
* 従来の同期的な関数 `.complete()` / Kotlin Coroutines の中断関数 `.await()` のどちらにも対応しています。コールバックスタイル `.queue {}` にも対応しています。タイムアウトも設定可能です。
* カーソル操作があるエンドポイントでは `.next` や `.untilLast()` といったメソッドでページングできます。

もし未対応のエンドポイントやバグを発見した際は, お気軽に [Issue](https://github.com/NephyProject/Penicillin/issues) を立ててください。すぐに対応します。

## Quick example

```kotlin

import jp.nephy.penicillin.PenicillinClient

fun main() {
    val client = PenicillinClient {
        application("ConsumerKey", "ConsumerSecret")
        token("AccessToken", "AccessToken Secret")
    }

    // gets tweets from @realdonaldtrump up to 100.
    client.timeline.user(screenName = "realdonaldtrump", count = 100).await().forEach { status ->
        // prints status text.
        println(status.text)
    }

    // disposes PenicillinClient
    client.close()
}
```

More examples of Penicillin can be found at [Wiki](https://github.com/NephyProject/Penicillin/wiki/Sample).

## Setup

Latest Penicillin version is [![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22).

### Gradle

```groovy
buildscript {
    ext.kotlin_version = "1.3.11"
    ext.penicillin_version = "Put desired Penicillin version here"

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        classpath "org.jetbrains.kotlin:kotlin-serialization:$kotlin_version"
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven { url "https://kotlin.bintray.com/ktor" }
    maven { url "https://kotlin.bintray.com/kotlinx" }
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"

    compile "jp.nephy:penicillin:$penicillin_version"
}
```

## Contributing

* [Guide](https://github.com/NephyProject/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/NephyProject/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)

## License

Penicillin is provided under MIT license. A copy of MIT license of Nephy Project is available [here](https://nephy.jp/license/mit).

Copyright (c) 2017- Nephy Project.
