/*
 * The MIT License (MIT)
 *
 *     Copyright (c) 2017-2020 StarryBlueSky
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

@file:Suppress("KDocMissingDocumentation", "PublicApiImplicitType")

import com.adarshr.gradle.testlogger.theme.ThemeType
import java.nio.file.Files
import java.nio.file.Paths

val githubOrganizationName = "StarryBlueSky"
val githubRepositoryName = "Penicillin"
val packageGroupId = "blue.starry"
val packageName = "Penicillin"
val packageVersion = Version(6, 0, 0)
val packageDescription = "Full-featured Twitter API wrapper for Kotlin."

object ThirdpartyVersion {
    // For Kotlin/Common
    const val Ktor = "1.4.3"
    const val JsonKt = "6.0.0"
    const val uuid = "0.2.3"
    const val KotlinxDatetime = "0.1.1"

    // For Kotlin/JS
    const val crypto_js = "4.0.0"

    // For testing
    const val JUnit = "5.7.0"
    const val TwitterText = "3.1.0"
    const val Guava = "29.0-jre"

    // For logging
    const val KotlinLogging = "2.0.3"
    const val Logback = "1.2.3"
    const val jansi = "1.18"
}

plugins { 
    kotlin("multiplatform") version "1.4.20"

    // For testing
    id("com.adarshr.test-logger") version "2.1.1"
    id("net.rdrei.android.buildtimetracker") version "0.11.0"

    // For publishing
    `maven-publish`
    id("com.jfrog.artifactory") version "4.17.2"
    
    // For documentation
    id("org.jetbrains.dokka") version "1.4.20"
}

data class Version(val major: Int, val minor: Int, val patch: Int) {
    val label: String
        get() = "$major.$minor.$patch"
}

// cannot be companion object
val isEAPBuild: Boolean
    get() = hasProperty("snapshot") || System.getenv("SNAPSHOT") != null

fun incrementBuildNumber(): Int {
    val path = Paths.get(buildDir.absolutePath, "build-number-${packageVersion.label}.txt")
    val number = if (Files.exists(path)) {
        path.toFile().readText().toIntOrNull()
    } else {
        null
    }?.coerceAtLeast(0)?.plus(1) ?: 1

    path.toFile().writeText(number.toString())

    return number
}

/*
 * Dependencies
 */

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://dl.bintray.com/starry-blue-sky/stable")
    maven(url = "https://dl.bintray.com/starry-blue-sky/dev")
}

kotlin {
    explicitApiWarning()
    // explicitApi()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js(BOTH) {
        nodejs()
        browser {
            testTask {
                useKarma {
                    useChrome()
                }
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                // Kotlin
                api(kotlin("stdlib"))

                api("io.ktor:ktor-client-core:${ThirdpartyVersion.Ktor}")
                api("blue.starry:jsonkt:${ThirdpartyVersion.JsonKt}")
                api("com.benasher44:uuid:${ThirdpartyVersion.uuid}")
                api("org.jetbrains.kotlinx:kotlinx-datetime:${ThirdpartyVersion.KotlinxDatetime}")
                api("io.github.microutils:kotlin-logging:${ThirdpartyVersion.KotlinLogging}")
            }
        }
        commonTest {
            dependencies {
                // Kotlin
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        named("jvmMain") {
        }
        named("jvmTest") {
            dependencies {
                // Kotlin
                implementation(kotlin("reflect"))
                implementation(kotlin("test"))

                // Ktor clients
                implementation("io.ktor:ktor-client-apache:${ThirdpartyVersion.Ktor}")
                implementation("io.ktor:ktor-client-cio:${ThirdpartyVersion.Ktor}")
                implementation("io.ktor:ktor-client-jetty:${ThirdpartyVersion.Ktor}")
                implementation("io.ktor:ktor-client-okhttp:${ThirdpartyVersion.Ktor}")
                implementation("io.ktor:ktor-client-mock-jvm:${ThirdpartyVersion.Ktor}")

                // For test
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter:${ThirdpartyVersion.JUnit}")
                implementation("com.twitter.twittertext:twitter-text:${ThirdpartyVersion.TwitterText}")
                implementation("com.google.guava:guava:${ThirdpartyVersion.Guava}")

                // For logging
                implementation("ch.qos.logback:logback-core:${ThirdpartyVersion.Logback}")
                implementation("ch.qos.logback:logback-classic:${ThirdpartyVersion.Logback}")
                implementation("org.fusesource.jansi:jansi:${ThirdpartyVersion.jansi}")
            }
        }

        named("jsMain") {
            dependencies {
                api("io.ktor:ktor-client-js:${ThirdpartyVersion.Ktor}")
                api("blue.starry:jsonkt-js:${ThirdpartyVersion.JsonKt}")

                // Platform specific
                implementation(npm("crypto-js", ThirdpartyVersion.crypto_js))
            }
        }
        named("jsTest") {
            dependencies {
                // Kotlin
                implementation(kotlin("test-js"))

                // Ktor clients
                implementation("io.ktor:ktor-client-mock-js:${ThirdpartyVersion.Ktor}")
            }
        }
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                apiVersion = "1.4"
                languageVersion = "1.4"
                // allWarningsAsErrors = true
                verbose = true
            }
        }
    }

    sourceSets.all {
        languageSettings.progressiveMode = true
        languageSettings.useExperimentalAnnotation("kotlin.Experimental")
    }
}

/*
 * Tests
 */

buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["barstyle"] = "ascii"
            options["shortenTaskNames"] = "false"
        }
    }
}

testlogger {
    theme = ThemeType.MOCHA
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

/*
 * Publishing
 */

var buildNumber = incrementBuildNumber()
project.group = packageGroupId
project.version = if (isEAPBuild) {
    "${packageVersion.label}-eap-${buildNumber}"
} else {
    packageVersion.label
}

tasks {
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("kdoc"))

        dokkaSourceSets.all {

            jdkVersion.set(8)
            includeNonPublic.set(false)
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            skipDeprecated.set(true)
        }
    }

    register<Jar>("kdocJar") {
        from(dokkaHtml)
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
    }
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            name.set(rootProject.name)
            description.set(packageDescription)
            url.set("https://github.com/$githubOrganizationName/$githubRepositoryName")
            licenses {
                license {
                    name.set("The MIT Licence")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    name.set("Nep")
                    email.set("spica@starry.blue")
                    organization.set("github")
                    organizationUrl.set("https://github.com/$githubOrganizationName")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/$githubOrganizationName/$githubRepositoryName.git")
                developerConnection.set("scm:git:ssh://github.com:$githubOrganizationName/$githubRepositoryName.git")
                url.set("https://github.com/$githubOrganizationName/$githubRepositoryName/tree/master")
            }
        }

        artifact(tasks["kdocJar"])
    }

    val user = System.getenv("BINTRAY_USER")
    val key = System.getenv("BINTRAY_KEY")

    if (user != null && key != null) {
        repositories {
            maven {
                name = packageName
                description = packageDescription

                val repo = if (isEAPBuild) "dev" else "stable"
                url = uri("https://api.bintray.com/maven/starry-blue-sky/$repo/$name/;publish=1;override=1")

                credentials {
                    username = user
                    password = key
                }
            }
        }
    }
}

artifactory {
    setContextUrl("http://oss.jfrog.org")

    publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        repository(delegateClosureOf<groovy.lang.GroovyObject> {
            setProperty("repoKey", "oss-snapshot-local")
            setProperty("username", System.getProperty("BINTRAY_USER"))
            setProperty("password", System.getProperty("BINTRAY_KEY"))
            setProperty("maven", true)
        })

        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            invokeMethod("publications", publishing.publications.names.toTypedArray())
            setProperty("publishArtifacts", true)
            setProperty("publishPom", true)
        })
    })

    resolve(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig> {
        setProperty("repoKey", "jcenter")
    })

    clientConfig.info.buildNumber = buildNumber.toString()
}
