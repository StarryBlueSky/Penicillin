@file:Suppress("KDocMissingDocumentation", "PublicApiImplicitType")

import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.breadmoirai.githubreleaseplugin.ChangeLogSupplier
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val githubOrganizationName = "NephyProject"
val githubRepositoryName = "Penicillin"
val packageGroupId = "jp.nephy"
val packageName = "Penicillin"
val packageVersion = Version(4, 1, 3)
val packageDescription = "Full-featured Twitter API wrapper for Kotlin."

val ktorVersion = "1.2.0-alpha-2"
val spekVersion = "2.0.2"

plugins { 
    kotlin("jvm") version "1.3.30"

    // For testing
    id("com.adarshr.test-logger") version "1.6.0"
    id("build-time-tracker") version "0.11.1"

    // For publishing
    id("maven-publish")
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.breadmoirai.github-release") version "2.2.8"
    
    // For documentation
    id("org.jetbrains.dokka") version "0.9.18"
}

fun Project.property(key: String? = null) = object: ReadOnlyProperty<Project, String?> {
    override fun getValue(thisRef: Project, property: KProperty<*>): String? {
        val name = key ?: property.name
        return (properties[name] ?: System.getProperty(name) ?: System.getenv(name))?.toString()
    }
}

/*
 * Dependencies
 */

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://kotlin.bintray.com/kotlin-eap")
    maven(url = "https://dl.bintray.com/spekframework/spek-dev")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-apache:$ktorVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("io.ktor:ktor-client-jetty:$ktorVersion")
    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock-jvm:$ktorVersion")

    implementation("jp.nephy:jsonkt:4.9")

    testImplementation("com.twitter.twittertext:twitter-text:3.0.1")
    testImplementation("com.google.guava:guava:27.1-jre")
    
    // For testing
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly(kotlin("reflect"))
    
    // For logging
    implementation("io.github.microutils:kotlin-logging:1.6.26")
    testImplementation("ch.qos.logback:logback-core:1.2.3")
    testImplementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("org.fusesource.jansi:jansi:1.17.1")
}

/*
 * Compilations
 */

tasks.named<KotlinCompile>("compileKotlin") {
    kotlinOptions { 
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
    }
}

tasks.named<KotlinCompile>("compileTestKotlin") {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
    }
}

/*
 * Versioning
 */

data class Version(val major: Int, val minor: Int, val patch: Int) {
    val label: String
        get() = "$major.$minor.$patch"
}

val isEAPBuild: Boolean
    get() = hasProperty("snapshot")

fun incrementBuildNumber(): Int {
    val buildNumberPath = Paths.get(buildDir.absolutePath, "build-number-${packageVersion.label}.txt")
    val buildNumber = if (Files.exists(buildNumberPath)) {
        buildNumberPath.toFile().readText().toIntOrNull()
    } else {
        null
    }?.coerceAtLeast(0)?.plus(1) ?: 1

    buildNumberPath.toFile().writeText(buildNumber.toString())
    
    return buildNumber
}

project.group = packageGroupId
project.version = if (isEAPBuild) {
    "${packageVersion.label}-eap-${incrementBuildNumber()}"
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
    
    jdkVersion = 8
    includeNonPublic = false
    reportUndocumented = true
    skipEmptyPackages = true
    skipDeprecated = false
}

val dokkaJavadoc = task<DokkaTask>("dokkaJavadoc") {
    // Maybe prefer "javadoc"?
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"

    jdkVersion = 8
    includeNonPublic = false
    reportUndocumented = false
    skipEmptyPackages = true
    skipDeprecated = false
}

/*
 * Publishing
 */

val jar = tasks.named<Jar>("jar").get()

if (isEAPBuild) {
    jar.destinationDir.listFiles().forEach {
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
        repo = githubRepositoryName.toLowerCase()
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
            released = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").format(Date())
            vcsTag = project.version.toString()
        }
    }
}

tasks.named<BintrayUploadTask>("bintrayUpload") {
    dependsOn("publishToMavenLocal")
}

val githubToken by property()

if (hasProperty("github")) {
    githubRelease {
        token(githubToken)
        val assets = jar.destinationDir.listFiles { _, filename ->
            project.version.toString() in filename && filename.endsWith(".jar")
        }
        releaseAssets(*assets)

        owner(githubOrganizationName)
        repo(githubRepositoryName)

        tagName("v${project.version}")
        releaseName("v${project.version}")
        targetCommitish("master")
        draft(false)
        prerelease(false)
        overwrite(false)

        changelog(closureOf<ChangeLogSupplier> {
            currentCommit("HEAD")
            lastCommit("HEAD~10")
            options(listOf("--format=oneline", "--abbrev-commit", "--max-count=50", "graph"))
        })

        fun buildChangelog(): String {
            return try {
                changelog().call().lines().takeWhile {
                    "Version bump" !in it
                }.joinToString("\n") {
                    val (tag, message) = it.split(" ", limit = 2)
                    "| $tag | $message |"
                }
            } catch (e: Exception) {
                ""
            }
        }

        body {
            buildString {
                appendln("## Version\n")
                appendln("**Latest** Penicillin version: [![Bintray](https://api.bintray.com/packages/nephyproject/penicillin/Penicillin/images/download.svg)](https://bintray.com/nephyproject/penicillin/Penicillin/_latestVersion)")
                appendln("The latest release build: `${project.version}`\n")

                appendln()

                appendln("## Changelogs\n")
                appendln("| Commits | Message |")
                appendln("|:------------:|:-----------|")
                append(buildChangelog())
            }
        }
    }
}
