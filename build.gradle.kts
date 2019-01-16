
import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.breadmoirai.ChangeLogSupplier
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val githubOrganizationName = "NephyProject"
val githubRepositoryName = "Penicillin"
val packageGroupId = "jp.nephy"
val packageName = "Penicillin"
val packageVersion = Version(4, 0, 3)
val packageDescription = "Full-featured Twitter API wrapper for Kotlin."

val ktorVersion = "1.1.1"

plugins { 
    kotlin("jvm") version "1.3.20-eap-100"
    id("kotlinx-serialization") version "1.3.20-eap-100"

    // For testing
    id("com.adarshr.test-logger") version "1.6.0"
    id("build-time-tracker") version "0.11.0"
    
    // For publishing
    id("maven-publish")
    id("signing")
    id("com.github.breadmoirai.github-release") version "2.2.3"
    
    // For documentation
    id("org.jetbrains.dokka") version "0.9.17"
}

buildscript { 
    dependencies {
        // Issue: https://github.com/BreadMoirai/github-release-gradle-plugin/issues/11
        classpath("com.squareup.okhttp3:okhttp:3.12.0")
        classpath("com.j256.simplemagic:simplemagic:1.10")
        classpath("org.zeroturnaround:zt-exec:1.10")
    }
}

/*
 * Extensions
 */

inline fun <reified T: Task> Project.task(name: String, crossinline block: T.() -> Unit): T {
    return (tasks.findByName(name) as? T ?: task<T>(name)).apply(block)
}

fun Project.property(key: String? = null) = object: ReadOnlyProperty<Project, String?> {
    override fun getValue(thisRef: Project, property: KProperty<*>): String? {
        return properties[key ?: property.name]?.toString()
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
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-apache:$ktorVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("io.ktor:ktor-client-jetty:$ktorVersion")
    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")

    api("jp.nephy:jsonkt:4.3")
    testImplementation("com.twitter.twittertext:twitter-text:3.0.1")

    implementation("io.github.microutils:kotlin-logging:1.6.22")
    testApi("ch.qos.logback:logback-core:1.2.3")
    testApi("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("org.fusesource.jansi:jansi:1.17.1")
}

/*
 * Compilations
 */

task<KotlinCompile>("compileKotlin") {
    kotlinOptions { 
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
    }
}

task<KotlinCompile>("compileTestKotlin") {
    kotlinOptions {
        jvmTarget = "1.8"
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

/*
 * Documentation
 */

val dokka = task<DokkaTask>("dokka") {
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

val jar = task<Jar>("jar") {}

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

val sonatypeUsername by property()
val sonatypePassword by property()

publishing {
    repositories {
        maven {
            name = "Local"
            url = Paths.get(buildDir.absolutePath, "maven-local").toUri()
        }
        
        if (!isEAPBuild) {
            maven {
                name = "Sonatype"
                url = URI("https://oss.sonatype.org/service/local/staging/deploy/maven2")

                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        } else {
            jar.destinationDir.listFiles().forEach { 
                it.delete()
            }
        }
    }

    publications {
        create<MavenPublication>("kotlin") {
            artifact(jar)
            artifact(sourcesJar)
            artifact(javadocJar)

            pom {
                name.set(packageName)
                description.set(packageDescription)
                url.set("https://github.com/$githubOrganizationName/$githubRepositoryName")
                packaging = "jar"

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("SlashNephy")
                        name.set("Slash Nephy")
                        email.set("admin@nephy.jp")
                    }
                }

                scm {
                    url.set("https://github.com/$githubOrganizationName/$githubRepositoryName")
                    connection.set("scm:git:https://github.com/$githubOrganizationName/$githubRepositoryName.git")
                    developerConnection.set("scm:git:git@github.com:$githubOrganizationName/$githubRepositoryName.git")
                    tag.set("HEAD")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/$githubOrganizationName/$githubRepositoryName/issues")
                }
            }
        }
    }
}

signing {
    setRequired({ gradle.taskGraph.hasTask("publish") })
    if (!isEAPBuild) {
        sign(publishing.publications)
    }
}

val githubToken by property()

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
            appendln("**Latest** Penicillin version: [![Maven Central](https://img.shields.io/maven-central/v/jp.nephy/penicillin.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22jp.nephy%22)\n")
            appendln("The latest release build: `${project.version}`\n")
            
            appendln()
            
            appendln("## Changelogs\n")
            appendln("| Commits | Message |")
            appendln("|:------------:|:-----------|")
            append(buildChangelog())
        }
    }
}

task<Task>("release") {
    dependsOn("publish")
    if (githubToken != null && !isEAPBuild) {
        dependsOn("githubRelease")
    }
}
