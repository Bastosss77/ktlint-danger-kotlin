import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
}

group = "org.jazzilla.danger.ktlint"
version = "0.1.0"

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
