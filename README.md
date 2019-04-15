# Penicillin: Full-featured Twitter API wrapper for Kotlin

[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.30-blue.svg)](http://kotlinlang.org)
[![Stable](https://img.shields.io/github/release/NephyProject/Penicillin.svg?label=Stable)](https://github.com/NephyProject/Penicillin/releases/latest)
[![Dev](https://img.shields.io/bintray/v/nephyproject/penicillin/Penicillin.svg?label=EAP)](https://bintray.com/nephyproject/penicillin/Penicillin/_latestVersion)
[![License](https://img.shields.io/github/license/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/blob/master/LICENSE)
[![Issues](https://img.shields.io/github/issues/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/NephyProject/Penicillin.svg)](https://github.com/NephyProject/Penicillin/pulls)

* Supports all the public Twitter API endpoints except Enterprise APIs.
* Supports the following authenticating methods: OAuth 1.0a, OAuth 2.0
* Supports some private Twitter API endpoints such as Poll Tweets.
* Endpoint's parameters are resolved as Kotlin "Typed-safe Named Parameter".
* Penicillin has model classes. So endpoint's response is easy to use.
* API execution supports classic function `.complete()`, suspend function `.await()`, deferred operation `.async()` and callback style `.queue {}`.
* Cursor APIs such as `friends/list` have methods named `.next`, `.untilLast()`. It makes paging easy.

KDoc is available at [docs.nephy.jp](https://docs.nephy.jp/penicillin). Documentation is currently WIP :(

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
    client.timeline.user(screenName = "realdonaldtrump", count = 100).await().forEach { status ->
        // prints status text.
        println(status.text)
    }

    // Disposes ApiClient
    client.close()
}
```

More examples of Penicillin can be found at [Wiki](https://github.com/NephyProject/Penicillin/wiki/Sample). Please feel free to create [new issue](https://github.com/NephyProject/Penicillin/issues/new/choose) if you have any questions.

## Setup

Latest Penicillin version is [![Bintray](https://api.bintray.com/packages/nephyproject/penicillin/Penicillin/images/download.svg)](https://bintray.com/nephyproject/penicillin/Penicillin/_latestVersion).  
Releases are available at [Bintray](https://bintray.com/nephyproject/penicillin/Penicillin). EAP builds are also available. Every commit is published as EAP build.  

You may choose preferred Ktor HttpClient Engine. We recommend using `Apache` or `CIO` engine.  
Full engine list is available at https://ktor.io/clients/http-client/engines.html.

Penicillin depends on `kotlinx.serialization`. So you need to add "https://kotlin.bintray.com/kotlinx" repository below.

### Gradle Kotlin DSL

We recommend using Gradle Kotlin DSL instead of old build.gradle.  

#### build.gradle.kts

```kotlin
val penicillinVersion = "PUT desired Penicillin version here"
val ktorVersion = "1.1.3"

plugins { 
    kotlin("jvm") version "1.3.21"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://kotlin.bintray.com/kotlin-eap")
    maven(url = "https://dl.bintray.com/nephyproject/penicillin")
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

### Gradle

#### build.gradle

```groovy
buildscript {
    ext.penicillin_version = "PUT desired Penicillin version here"
    ext.kotlin_version = "1.3.21"
    ext.ktor_version = "1.1.3"

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
    maven { url "https://kotlin.bintray.com/kotlin-eap" }
    maven { url "https://dl.bintray.com/nephyproject/penicillin" } 
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    implementation "jp.nephy:penicillin:$penicillin_version"
    
    implementation "io.ktor:ktor-client-apache:$ktor_version"
    // implementation "io.ktor:ktor-client-cio:$ktor_version"
    // implementation "io.ktor:ktor-client-jetty:$ktor_version"
    // implementation "io.ktor:ktor-client-okhttp:$ktor_version"
}
```

## Multiplatform Support

Currently Penicillin does **NOT** support non-JVM environment.  

In the future, Penicillin is plan to support Kotlin/Multiplatform.

## Contributing

* [Guide](https://github.com/NephyProject/Penicillin/blob/master/CONTRIBUTING.md)
* [Pull Request Template](https://github.com/NephyProject/Penicillin/blob/master/PULL_REQUEST_TEMPLATE.md)
* [Issue: Bug Report Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/bug-report.md)
* [Issue: Feature Request Template](https://github.com/NephyProject/Penicillin/blob/master/.github/ISSUE_TEMPLATE/feature-request.md)

## License

Penicillin is provided under MIT license. A copy of MIT license of Nephy Project is available [here](https://nephy.jp/license/mit).

Copyright (c) 2017-2019 Nephy Project.
