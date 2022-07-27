plugins {
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    this.testLogging {
        this.showStandardStreams = true
    }
}