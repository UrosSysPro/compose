import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.compose") version "1.8.1"
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
    id("com.gradleup.shadow") version "9.0.0-beta17"
    id("org.openjfx.javafxplugin") version "0.0.7"
    kotlin("plugin.serialization") version "2.1.20"
}

group = "net.systemvi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    gradlePluginPortal()
    google()
}

dependencies {
    //serial connection
	implementation("io.github.java-native:jssc:2.9.6")
    //compose multiplatform
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.9.1")
    implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")
    implementation("com.mohamedrejeb.dnd:compose-dnd:0.3.0")
    //logger
    implementation("org.slf4j:slf4j-simple:2.0.17")
    //arrow kt
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("io.arrow-kt:arrow-core-serialization:2.1.2")
    implementation("io.arrow-kt:arrow-optics:2.1.2")
    ksp("io.arrow-kt:arrow-optics-ksp-plugin:2.1.0")
    // material 3 and colors
    implementation("com.materialkolor:material-kolor:3.0.0-alpha04")
    implementation(compose.material3)
    // csg model exporter
    implementation("eu.mihosoft.vvecmath:vvecmath:0.3.8")
    implementation(files("./libs/jcsg.jar"))
}

javafx {
    modules = listOf( "javafx.graphics", "javafx.fxml")
}

tasks.withType<Jar> {
    manifest {
        attributes["Class-Path"] = configurations.runtimeClasspath.get().files.joinToString(" ") { it.name }
        attributes["Main-Class"] = "net.systemvi.configurator.MainKt"
    }
}

compose.desktop {
    application {
        mainClass = "net.systemvi.configurator.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe, TargetFormat.Pkg)
            packageName = "compose"
            packageVersion = "1.0.0"
        }
    }
}
