# Penicillin: Modern powerful Twitter API wrapper for Kotlin Multiplatform

[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![stable](https://img.shields.io/bintray/v/nephyproject/stable/Penicillin.svg?label=stable)](https://github.com/NephyProject/Penicillin/releases/latest)
[![dev](https://img.shields.io/bintray/v/nephyproject/dev/Penicillin.svg?label=dev)](https://bintray.com/nephyproject/dev/Penicillin/_latestVersion)
[![license](https://img.shields.io/github/license/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/blob/master/LICENSE)
[![issues](https://img.shields.io/github/issues/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/issues)
[![pull requests](https://img.shields.io/github/issues-pr/StarryBlueSky/Penicillin.svg)](https://github.com/StarryBlueSky/Penicillin/pulls)

* Supports JVM, Android (API level >= 24), JS (Both browsers and NodeJS).  
* Supports all the public Twitter API endpoints.  
* Supports the following authenticating methods: OAuth 1.0a, OAuth 2.0  
* Supports some private Twitter API endpoints such as Poll Tweets.  
* Endpoint's parameters are resolved as Kotlin "Typed-safe Named Parameter".  
* Penicillin has model classes. So responses are easy to use.  
* API's execution supports classical blocking function `.complete()`, suspend function `.execute()`, deferred operation `.executeAsync()` and callback style `.queue {}`.  
* Cursor APIs such as `friends/list` have methods named `.next`, `.untilLast()`. It makes paging easy.  

KDoc is available at [docs.starry.blue](https://docs.starry.blue/penicillin).  

## Quick Example

```kotlin
suspend fun main() {
    // Creates new ApiClient
    PenicillinClient {
        account {
            application("ConsumerKey", "ConsumerSecret")
            token("AccessToken", "AccessToken Secret")
        }
    }.use {
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

Latest Penicillin version is [![Stable](https://img.shields.io/bintray/v/nephyproject/stable/Penicillin.svg?label=stable)](https://bintray.com/nephyproject/stable/Penicillin/_latestVersion) or [![Dev](https://img.shields.io/bintray/v/nephyproject/dev/Penicillin.svg?label=dev)](https://bintray.com/nephyproject/dev/Penicillin/_latestVersion).  

Stable releases are available at [Bintray](https://bintray.com/nephyproject/stable/Penicillin). EAP builds are also available ([Dev Repository](https://bintray.com/nephyproject/dev/Penicillin)). Every commit is published as EAP build.  

You may choose preferred Ktor HttpClient Engine. We recommend using `Apache` or `CIO` engine on JVM.  
Full engine list is available at <https://ktor.io/clients/http-client/engines.html>.

### Gradle

We recommend using Gradle Kotlin DSL instead of classic build.gradle.  

#### build.gradle.kts:

```kotlin
val ktorVersion = "1.3.2"

repositories {
    mavenCentral()
    jcenter()

    maven(url = "https://dl.bintray.com/nephyproject/stable")
    // or dev repository if EAP builds preferred
    // maven(url = "https://dl.bintray.com/nephyproject/dev")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                // for common; In many cases, this is not necessary.
                implementation("blue.starry:penicillin-common:$PenicillinVersion")
            }
        }

        named("jvmMain") {
            // for JVM (Android)
            implementation("blue.starry:penicillin:$PenicillinVersion")
        }

        named("jsMain") {
            // for JS
            implementation("blue.starry:penicillin-js:$PenicillinVersion")
        }
    }
}
```

## Compatibility

Currently Penicillin works on JVM (Java 8), Android (API level >= 24) or JS (both browsers and NodeJS).  

In the future, Penicillin is plan to support Kotlin/Native. It brings the benefits of reuse for Kotlin code and saves you from wasting time.  
For example, if you only write Kotlin code once, it can be compiled for JVM, JavaScript, iOS, Android, Windows, macOS and so on.  

In Android development, we confirmed that Penicillin works only on API level 24 or greater. It's caused by Ktor-side restriction. Detail information is [here](https://ktor.io/quickstart/faq.html#android-support).

## Contributing

* [Guide](https://github.com/NephyProject/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/NephyProject/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)
* [Issue: Feature Request Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/feature-request.md)

## License

Penicillin is provided under the MIT license.  

Copyright (c) 2017-2020 StarryBlueSky.
