# Penicillin: Modern powerful Twitter API wrapper for Kotlin Multiplatform

[![Kotlin](https://img.shields.io/badge/Kotlin-1.4.30-blue.svg)](https://kotlinlang.org)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/StarryBlueSky/Penicillin)](https://github.com/StarryBlueSky/Penicillin/releases)
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/StarryBlueSky/Penicillin/Check)](https://github.com/StarryBlueSky/Penicillin)
[![license](https://img.shields.io/github/license/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/blob/master/LICENSE)
[![issues](https://img.shields.io/github/issues/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/issues)
[![pull requests](https://img.shields.io/github/issues-pr/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/pulls)

* Supports JVM (Java >= 8), Android (API level >= 24), JavaScript (Browsers and NodeJS).  
* Supports all the public Twitter API endpoints.  
* Supports the following authenticating methods: OAuth 1.0a, OAuth 2.0  
* Supports some private Twitter API endpoints such as Poll Tweets.  
* Endpoint's parameters are resolved as Kotlin "Typed-safe Named Parameter".  
* Penicillin has model classes. So responses are easy to use.  
* API's execution supports classical blocking function `.complete()`, suspend function `.execute()`, deferred operation `.executeAsync()` and callback style `.queue {}`.  
* Cursor APIs such as `friends/list` have methods named `.next`, `.untilLast()`. It makes paging easy.  

KDoc is available on [GitHub Pages](https://starrybluesky.github.io/Penicillin/penicillin).  

## Quick Example

```kotlin
suspend fun main() {
    // Creates new ApiClient
    PenicillinClient {
        account {
            application("ConsumerKey", "ConsumerSecret")
            token("AccessToken", "AccessToken Secret")
        }
    }.use { client ->
        // Retrieves user timeline from @realdonaldtrump up to 100.
        client.timeline.userTimeline(screenName = "realdonaldtrump", count = 100).execute().forEach { status ->
            // Prints status text.
            println(status.text)
        }
    }
}
```

More examples of Penicillin can be found at [Wiki](https://github.com/StarryBlueSky/Penicillin/wiki/Sample). Please feel free to create [new issue](https://github.com/StarryBlueSky/Penicillin/issues/new/choose) if you have any questions.

## Get Started

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/StarryBlueSky/Penicillin)](https://github.com/StarryBlueSky/Penicillin/releases)

### Gradle

#### build.gradle.kts:

```kotlin
repositories {
    mavenCentral()

    // for internal dependencies
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation("blue.starry:penicillin:$PenicillinVersion")
    
    // choose one of https://ktor.io/clients/http-client/engines.html
    implementation("io.ktor:ktor-client-apache:1.5.2")
    implementation("io.ktor:ktor-client-cio:1.5.2")
}
```

## Compatibility

Currently, Penicillin works on JVM (Java 8 or above), Android (API level 24 or above) or JS (both browsers and NodeJS).  

In the future, Penicillin is plan to support Kotlin/Native. It brings the benefits of reuse for Kotlin code and saves you from wasting time.  
For example, if you only write Kotlin code once, it can be compiled for JVM, JavaScript, iOS, Android, Windows, macOS and so on.  

In Android development, we confirmed that Penicillin works only on API level 24 or above. It's caused by Ktor-side restriction. Detail information is [here](https://ktor.io/quickstart/faq.html#android-support).

## Contributing

* [Guide](https://github.com/StarryBlueSky/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/StarryBlueSky/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/StarryBlueSky/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)
* [Issue: Feature Request Template](https://github.com/StarryBlueSky/Penicillin/blob/master/.github/ISSUE_TEMPLATE/feature-request.md)

## License

Penicillin is provided under the MIT license.  

Copyright (c) 2017-2021 StarryBlueSky.
