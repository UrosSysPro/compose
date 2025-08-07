plugins {
    kotlin("jvm")
//    id("org.jetbrains.compose") version "1.8.1"
//    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
//    id("com.gradleup.shadow") version "9.0.0-beta17"
//    id("org.openjfx.javafxplugin") version "0.0.7"
//    kotlin("plugin.serialization") version "2.1.20"
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    gradlePluginPortal()
    google()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-2.0.0")
}