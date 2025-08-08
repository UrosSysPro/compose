plugins {
    kotlin("jvm")
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