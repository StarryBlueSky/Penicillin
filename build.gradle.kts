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
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val githubOrganizationName = "StarryBlueSky"
val githubRepositoryName = "Penicillin"
val packageGroupId = "blue.starry"
val packageName = "Penicillin"
val packageVersion = Version(5, 0, 0)
val packageDescription = "Full-featured Twitter API wrapper for Kotlin."

object ThirdpartyVersion {
    const val Ktor = "1.3.2"
    const val JsonKt = "5.0.0-eap-27"

    // For testing
    const val Spek = "2.0.10"
    const val TwitterText = "3.0.1"
    const val Guava = "29.0-jre"

    // For logging
    const val KotlinLogging = "1.7.9"
    const val Logback = "1.2.3"
    const val jansi = "1.18"
}

plugins { 
    kotlin("jvm") version "1.3.72"

    // For testing
    id("com.adarshr.test-logger") version "2.0.0"
    id("build-time-tracker") version "0.11.1"

    // For publishing
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    
    // For documentation
    id("org.jetbrains.dokka") version "0.10.1"
}

fun Project.property(key: String? = null) = object: ReadOnlyProperty<Project, String?> {
    override fun getValue(thisRef: Project, property: KProperty<*>): String? {
        val name = key ?: property.name
        return (properties[name] ?: System.getProperty(name) ?: System.getenv(name))?.toString()
    }
}

data class Version(val major: Int, val minor: Int, val patch: Int) {
    val label: String
        get() = "$major.$minor.$patch"
}

// cannot be companion object
val isEAPBuild: Boolean
    get() = hasProperty("snapshot")

val buildNumber: Int
    get() {
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
    maven(url = "https://dl.bintray.com/nephyproject/stable")
    maven(url = "https://dl.bintray.com/nephyproject/dev")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-client-core-jvm:${ThirdpartyVersion.Ktor}")
    testImplementation("io.ktor:ktor-client-apache:${ThirdpartyVersion.Ktor}")
    testImplementation("io.ktor:ktor-client-cio:${ThirdpartyVersion.Ktor}")
    testImplementation("io.ktor:ktor-client-jetty:${ThirdpartyVersion.Ktor}")
    testImplementation("io.ktor:ktor-client-okhttp:${ThirdpartyVersion.Ktor}")
    testImplementation("io.ktor:ktor-client-mock-jvm:${ThirdpartyVersion.Ktor}")

    implementation("blue.starry:jsonkt:${ThirdpartyVersion.JsonKt}")

    testImplementation("com.twitter.twittertext:twitter-text:${ThirdpartyVersion.TwitterText}")
    testImplementation("com.google.guava:guava:${ThirdpartyVersion.Guava}")

    // For testing
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:${ThirdpartyVersion.Spek}")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:${ThirdpartyVersion.Spek}")
    testImplementation(kotlin("reflect"))

    // For logging
    implementation("io.github.microutils:kotlin-logging:${ThirdpartyVersion.KotlinLogging}")
    testImplementation("ch.qos.logback:logback-core:${ThirdpartyVersion.Logback}")
    testImplementation("ch.qos.logback:logback-classic:${ThirdpartyVersion.Logback}")
    testImplementation("org.fusesource.jansi:jansi:${ThirdpartyVersion.jansi}")
}

/*
 * Compilations
 */

tasks.named<KotlinCompile>("compileKotlin") {
    kotlinOptions { 
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
    }
}

tasks.named<KotlinCompile>("compileTestKotlin") {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
    }
}

project.group = packageGroupId
project.version = if (isEAPBuild) {
    "${packageVersion.label}-eap-${buildNumber}"
} else {
    packageVersion.label
}

/*
 * Tests
 */

buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["shortenTaskNames"] = "false"
        }
    }
}

testlogger {
    theme = ThemeType.MOCHA
}

tasks.named<Test>("test") {
    useJUnitPlatform {
        includeEngines("spek2")
    }
}

/*
 * Documentation
 */

val dokka = tasks.named<DokkaTask>("dokka") {
    outputFormat = "html"
    outputDirectory = "$buildDir/kdoc"
    
//    jdkVersion = 8
//    includeNonPublic = false
//    reportUndocumented = true
//    skipEmptyPackages = true
//    skipDeprecated = false
}

val dokkaJavadoc = task<DokkaTask>("dokkaJavadoc") {
    // Maybe prefer "javadoc"?
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"

//    jdkVersion = 8
//    includeNonPublic = false
//    reportUndocumented = false
//    skipEmptyPackages = true
//    skipDeprecated = false
}

/*
 * Publishing
 */

val jar = tasks.named<Jar>("jar").get()

if (isEAPBuild) {
    jar.destinationDir.listFiles()?.forEach {
        it.delete()
    }
}

val sourcesJar = task<Jar>("sourcesJar") {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

val javadocJar = task<Jar>("javadocJar") {
    dependsOn(dokkaJavadoc)
    
    classifier = "javadoc"
    from("$buildDir/javadoc")
}

val kdocJar = task<Jar>("kdocJar") {
    dependsOn(dokka)

    classifier = "kdoc"
    from("$buildDir/kdoc")
}

publishing {
    publications {
        create<MavenPublication>("kotlin") {
            from(components.getByName("java"))
            
            artifact(sourcesJar)
            artifact(javadocJar)
        }
    }
}

val bintrayUsername by property()
val bintrayApiKey by property()

bintray {
    setPublications("kotlin")
    
    user = bintrayUsername
    key = bintrayApiKey
    publish = true
    override = true
    
    pkg.apply { 
        repo = if (isEAPBuild) "dev" else "stable"
        userOrg = githubOrganizationName.toLowerCase()
        
        name = packageName
        desc = packageDescription
        
        setLicenses("MIT")
        publicDownloadNumbers = true
        
        githubRepo = "$githubOrganizationName/$githubRepositoryName"
        websiteUrl = "https://github.com/$githubOrganizationName/$githubRepositoryName"
        issueTrackerUrl = "https://github.com/$githubOrganizationName/$githubRepositoryName/issues"
        vcsUrl = "https://github.com/$githubOrganizationName/$githubRepositoryName.git"
        
        version.apply { 
            name = project.version.toString()
            desc = "$packageName ${project.version}"
            released = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(ZonedDateTime.now())
            vcsTag = project.version.toString()
        }
    }
}
