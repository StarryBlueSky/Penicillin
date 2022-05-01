# Penicillin: Modern powerful Twitter API wrapper for Kotlin Multiplatform

[![Kotlin](https://img.shields.io/badge/Kotlin-1.6-blue.svg)](https://kotlinlang.org)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/StarryBlueSky/Penicillin)](https://github.com/StarryBlueSky/Penicillin/releases)
[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/StarryBlueSky/Penicillin/Check)](https://github.com/StarryBlueSky/Penicillin)
[![license](https://img.shields.io/github/license/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/blob/master/LICENSE)
[![issues](https://img.shields.io/github/issues/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/issues)
[![pull requests](https://img.shields.io/github/issues-pr/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/pulls)

* Supports Java 8 or later, Android (API level 24 or higher), JavaScript target. 
* Supports all of Twitter's v1.1 public APIs and some v2 public and private APIs. 
* All endpoint parameters can be represented by named parameters.
* There are model classes for response JSON, which can be accessed type-safely.
* Paging APIs can be handled by [Flow](https://kotlinlang.org/docs/flow.html).

Documentation is available at [GitHub Pages](https://starrybluesky.github.io/Penicillin/).  

## Quick Example

```kotlin
suspend fun main() {
    // Create new PenicillinClient.
    PenicillinClient {
        account {
            application("ConsumerKey", "ConsumerSecret")
            token("AccessToken", "AccessToken Secret")
        }
    }.use { client ->
        // Retrieve up to 100 tweets from @POTUS.
        val timeline = client.timeline.userTimelineByScreenName(screenName = "POTUS", count = 100).execute()

        // The return value of the timeline is `JsonArrayResponse<Status>`. It implements `Iterable<T>`, which allows iterative operations.
        timeline.forEach { status ->
            // Print unescaped status text. If you want to get the raw html reference characters, you can use `textRaw`.
            println(status.text)
        }
    }
}
```

Other examples can be found at [Wiki](https://github.com/StarryBlueSky/Penicillin/wiki/Sample). If you have any questions, please let us know at [Issue](https://github.com/StarryBlueSky/Penicillin/issues/new).

## Get Started

Penicillin is now available in the Maven Central since version 6.1.0. The previous Bintray repository is no longer available.

[![GitHub release (latest by date)](https://img.shields.io/github/v/release/StarryBlueSky/Penicillin)](https://github.com/StarryBlueSky/Penicillin/releases)

### Gradle

#### build.gradle.kts:

```kotlin
dependencies {
    implementation("blue.starry:penicillin:$PenicillinVersion")
    
    // Choose your favorite engine from https://ktor.io/clients/http-client/engines.html
    // The version should match the version of Ktor that Penicillin is using.
    implementation("io.ktor:ktor-client-apache:1.6.8")
    implementation("io.ktor:ktor-client-cio:1.6.8")
}
```

## License

Penicillin is provided under the MIT license.  

Copyright (c) 2017-2022 StarryBlueSky.
