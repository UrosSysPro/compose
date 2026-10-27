plugins {
    kotlin("jvm")
}

dependencies {
    // Version comes from gradle/libs.versions.toml. KSP is versioned independently of Kotlin
    // since the 2.3.x line, so this no longer has to match the Kotlin version.
    implementation(libs.ksp.api)
}
