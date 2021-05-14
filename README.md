# Penicillin: Modern powerful Twitter API wrapper for Kotlin Multiplatform

[![Kotlin](https://img.shields.io/badge/Kotlin-1.5-blue.svg)](https://kotlinlang.org)
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

Documentation is available at [GitHub Pages](https://starrybluesky.github.io/Penicillin/penicillin).  

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
    implementation("io.ktor:ktor-client-apache:$KtorVersion")
    implementation("io.ktor:ktor-client-cio:$KtorVersion")
}
```

## License

Penicillin is provided under the MIT license.  

Copyright (c) 2017-2021 StarryBlueSky.
