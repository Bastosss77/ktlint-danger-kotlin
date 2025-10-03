import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
    id("com.vanniktech.maven.publish") version "0.34.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("systems.danger:danger-kotlin-sdk:1.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    testImplementation("io.mockk:mockk:1.14.6")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

configure<KtlintExtension> {
    version.set("1.7.1")

    reporters {
        reporter(ReporterType.JSON)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
        reporter(ReporterType.PLAIN)
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates("io.github.bastosss77", "ktlint-danger-kotlin", "0.1.0")

    pom {
        name.set("Ktlint Danger Kotlin")
        description.set("A Ktlint plugin for Danger Kotlin")
        inceptionYear.set("2025")
        url.set("https://github.com/Bastosss77/ktlint-danger-kotlin")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("Bastosss77")
                name.set("Bastosss77")
                url.set("https://github.com/Bastosss77")
                email.set("bastosss77@gmail.com")
            }
        }
        scm {
            url.set("https://github.com/Bastosss77/ktlint-danger-kotlin")
            connection.set("scm:git:git://github.com/Bastosss77/ktlint-danger-kotlin.git")
            developerConnection.set("scm:git:ssh://git@github.com/Bastosss77/ktlint-danger-kotlin.git")
        }
    }
}
