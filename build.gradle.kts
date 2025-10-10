import org.jetbrains.kotlin.gradle.dsl.JvmDefaultMode
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.mavenPublish)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.dangerSdk)
    implementation(platform(libs.jackson.bom))
    implementation(libs.jackson.xml)
    implementation(libs.jackson.kotlin)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.test)
}

kotlin {
    jvmToolchain(
        libs.versions.jvmVersion
            .get()
            .toInt(),
    )
}

configure<KtlintExtension> {
    version.set(libs.versions.ktlint.get())

    reporters {
        reporter(ReporterType.JSON)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.SARIF)
        reporter(ReporterType.PLAIN)
    }
}
group = libs.versions.group.get()
version = libs.versions.version.get()

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(group.toString(), "ktlint-danger-kotlin", version.toString())

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
