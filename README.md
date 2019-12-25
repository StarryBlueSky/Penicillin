# Penicillin: Modern Powerful Twitter API wrapper for Kotlin

[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.61-blue.svg)](https://kotlinlang.org)
[![Stable](https://img.shields.io/bintray/v/nephyproject/stable/Penicillin.svg?label=stable)](https://github.com/NephyProject/Penicillin/releases/latest)
[![Dev](https://img.shields.io/bintray/v/nephyproject/dev/Penicillin.svg?label=dev)](https://bintray.com/nephyproject/dev/Penicillin/_latestVersion)
[![License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![Issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/pulls)

* Supports all the public Twitter API endpoints except Enterprise APIs.
* Supports the following authenticating methods: OAuth 1.0a, OAuth 2.0
* Supports some private Twitter API endpoints such as Poll Tweets.
* Endpoint's parameters are resolved as Kotlin "Typed-safe Named Parameter".
* Penicillin has model classes. So endpoint's response is easy to use.
* API execution supports classical blocking function `.complete()`, suspend function `.await()`, deferred operation `.async()` and callback style `.queue {}`.
* Cursor APIs such as `friends/list` have methods named `.next`, `.untilLast()`. It makes paging easy.

KDoc is available at [docs.nephy.jp](https://docs.nephy.jp/penicillin).

## Quick example

```kotlin
suspend fun main() {
    // Creates new ApiClient
    val client = PenicillinClient {
        account {
            application("ConsumerKey", "ConsumerSecret")
            token("AccessToken", "AccessToken Secret")
        }
    }

    // Retrieves the user timeline from @realdonaldtrump up to 100.
    client.timeline.userTimeline(screenName = "realdonaldtrump", count = 100).await().forEach { status ->
        // Prints status text.
        println(status.text)
    }

    // Disposes ApiClient
    client.close()
}
```

More examples of Penicillin can be found at [Wiki](https://github.com/NephyProject/Penicillin/wiki/Sample). Please feel free to create [new issue](https://github.com/NephyProject/Penicillin/issues/new/choose) if you have any questions.

## Setup

Latest Penicillin version is [![Stable](https://img.shields.io/bintray/v/nephyproject/stable/Penicillin.svg?label=stable)](https://github.com/NephyProject/Penicillin/releases/latest) or [![Dev](https://img.shields.io/bintray/v/nephyproject/dev/Penicillin.svg?label=dev)](https://bintray.com/nephyproject/dev/Penicillin/_latestVersion).  

Stable releases are available at [Bintray](https://bintray.com/nephyproject/stable/Penicillin). EAP builds are also available ([Dev Repository](https://bintray.com/nephyproject/dev/Penicillin)). Every commit is published as EAP build.  

You may choose preferred Ktor HttpClient Engine. We recommend using `Apache` or `CIO` engine.  
Full engine list is available at <https://ktor.io/clients/http-client/engines.html>.

### Gradle

We recommend using Gradle Kotlin DSL instead of classic build.gradle.  

#### build.gradle.kts

```kotlin
val penicillinVersion = "4.2.3"
val ktorVersion = "1.3.0-rc"

plugins { 
    kotlin("jvm") version "1.3.61"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://kotlin.bintray.com/kotlin-eap")
    maven(url = "https://dl.bintray.com/nephyproject/stable")
    // Or dev repository
    // maven(url = "https://dl.bintray.com/nephyproject/dev")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("jp.nephy:penicillin:$penicillinVersion")
    
    implementation("io.ktor:ktor-client-apache:$ktorVersion")
    // implementation("io.ktor:ktor-client-cio:$ktorVersion")
    // implementation("io.ktor:ktor-client-jetty:$ktorVersion")
    // implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
}
```

## Compatibility

Currently Penicillin works on JVM (Java 8) or Android (API level 24 or greater).  

In the future, Penicillin is plan to support Kotlin/Multiplatform. It brings the benefits of reuse for Kotlin code and saves you from wasting time.  
For example, if you only write Kotlin code once, it can be compiled for JVM, JavaScript, iOS, Android, Windows, macOS and so on.  

In Android development, we confirmed that Penicillin works only on API level 24 or greater. It's caused by Ktor-side restriction. Detail information is [here](https://ktor.io/quickstart/faq.html#android-support).

## Contributing

* [Guide](https://github.com/NephyProject/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/NephyProject/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)
* [Issue: Feature Request Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/feature-request.md)

## License

Penicillin is provided under MIT license. A copy of MIT license of Nephy Project is available [here](https://nephy.jp/license/mit).

Copyright (c) 2017-2019 Nephy Project.
