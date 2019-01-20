# Penicillin: Full-featured Twitter API wrapper for Kotlin

[![Kotlin 1.3.20](https://img.shields.io/badge/Kotlin-1.3.20-blue.svg)](http://kotlinlang.org)
[![Bintray](https://api.bintray.com/packages/nephyproject/penicillin/Penicillin/images/download.svg)](https://bintray.com/nephyproject/penicillin/Penicillin/_latestVersion)
[![MIT License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/pulls)

* Enterprise API を除く, すべての公開 Twitter API と認証方式 (OAuth 1.0a, OAuth 2.0) に対応しています。
* 一部の非公開 API に対応しています(投票など)。今後もサポートを充実する予定です。
* エンドポイントのパラメータが名前付き引数として解決できるため, 簡単に API が利用できます。
* エンドポイントのレスポンスのモデルクラスが用意されているので, API の返り値の利用が容易にできます。
* 従来の同期的な関数 `.complete()` / Kotlin Coroutines の中断関数 `.await()` のどちらにも対応しています。コールバックスタイル `.queue {}` にも対応しています。タイムアウトも設定可能です。
* カーソル操作があるエンドポイントでは `.next` や `.untilLast()` といったメソッドでページングできます。

KDoc is available at [docs.nephy.jp](https://docs.nephy.jp/penicillin). Documentation is WIP :(

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
EAP builds are available at our [Jenkins](https://jenkins.nephy.jp/job/Penicillin/).

### Gradle Kotlin DSL

We recommend using Gradle Kotlin DSL instead of old build.gradle.  

#### build.gradle.kts

```kotlin
val penicillinVersion = "PUT desired Penicillin version here"
val ktorVersion = "1.1.1"

plugins { 
    kotlin("jvm") version "1.3.20"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://dl.bintray.com/nephyproject/penicillin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("jp.nephy:penicillin:$penicillinVersion")
    
    // Choose preferred Ktor Http Engine for JVM.
    // Full list is available at https://ktor.io/clients/http-client/engines.html.
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    // implementation("io.ktor:ktor-client-cio:$ktorVersion")
    // implementation("io.ktor:ktor-client-jetty:$ktorVersion")
    // implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
}
```

### Gradle

#### build.gradle

```groovy
buildscript {
    ext.penicillin_version = "PUT desired Penicillin version here"
    ext.kotlin_version = "1.3.20"
    ext.ktor_version = "1.1.1"

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven { url "https://kotlin.bintray.com/ktor" }
    maven { url "https://kotlin.bintray.com/kotlinx" }
    maven { url "https://dl.bintray.com/nephyproject/penicillin" } 
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation "jp.nephy:penicillin:$penicillin_version"
    
    // Choose preferred Ktor Http Engine for JVM.
    // Full list is available at https://ktor.io/clients/http-client/engines.html.
    implementation "io.ktor:ktor-client-apache:$ktor_version"
    // implementation "io.ktor:ktor-client-cio:$ktor_version"
    // implementation "io.ktor:ktor-client-jetty:$ktor_version"
    // implementation "io.ktor:ktor-client-okhttp:$ktor_version"
}
```

## Multiplatform Support

Currently Penicillin does **NOT** provide supports for non-JVM environment yet. Penicillin uses Json.kt which depends on JVM.  

Android has JVM environment, so you may use Penicillin to develop Android apps.  

In the future, Penicillin is plan to support Kotlin/Multiplatform.

## Contributing

* [Guide](https://github.com/NephyProject/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/NephyProject/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)
* [Issue: Feature Request Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/feature-request.md)

## License

Penicillin is provided under MIT license. A copy of MIT license of Nephy Project is available [here](https://nephy.jp/license/mit).

Copyright (c) 2017- Nephy Project.
