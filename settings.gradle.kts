pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven(url = "https://kotlin.bintray.com/kotlin-eap")
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlinx-serialization" -> {
                    useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
                }
                "com.jfrog.bintray" -> {
                    useModule("com.jfrog.bintray.gradle:gradle-bintray-plugin:${requested.version}")
                }
                "com.github.breadmoirai.github-release" -> {
                    useModule("gradle.plugin.com.github.breadmoirai:github-release:${requested.version}")
                }
                "org.jetbrains.dokka" -> {
                    useModule("org.jetbrains.dokka:dokka-gradle-plugin:${requested.version}")
                }
                "com.adarshr.test-logger" -> {
                    useModule("com.adarshr:gradle-test-logger-plugin:${requested.version}")
                }
                "build-time-tracker" -> {
                    useModule("net.rdrei.android.buildtimetracker:gradle-plugin:${requested.version}")
                }
            }
        }
    }
}

rootProject.name = "penicillin"

// enableFeaturePreview("GRADLE_METADATA")
