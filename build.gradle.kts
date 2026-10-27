import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20"
    id("org.jetbrains.compose") version "1.8.1"
    id("com.google.devtools.ksp") version "2.1.20-2.0.0"
}

group = "net.systemvi"

// Single source of truth for the release version.
// Override from CI or the command line, for example: -PappVersion=1.2.3
version = providers.gradleProperty("appVersion").getOrElse("1.0.0")

// jpackage cannot cross-compile, so the formats are chosen per host OS.
val osName = System.getProperty("os.name").lowercase()
val isWindows = osName.contains("win")
val isMacOs = osName.contains("mac")

// msi/exe require MAJOR.MINOR.BUILD (numeric only), deb requires a version starting with a digit.
val releaseVersion = version.toString().substringBefore('-').substringBefore('+')

kotlin {
    // Pins the JDK used to compile *and* the JDK that jpackage bundles into the installer.
    jvmToolchain(21)
}

dependencies {
    // serial connection
    implementation("io.github.java-native:jssc:2.9.6")

    // compose multiplatform
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose:2.9.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("com.mohamedrejeb.dnd:compose-dnd:0.3.0")

    // Material icons: JetBrains discontinued this artifact after 1.7.3, see AGENTS.md
    implementation("org.jetbrains.compose.material:material-icons-core:1.7.3")

    // logger
    implementation("org.slf4j:slf4j-simple:2.0.17")

    // arrow kt
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("io.arrow-kt:arrow-core-serialization:2.1.2")
    implementation("io.arrow-kt:arrow-optics:2.1.2")
    ksp("io.arrow-kt:arrow-optics-ksp-plugin:2.1.0")

    // material 3 and colors
    implementation("com.materialkolor:material-kolor:3.0.0-alpha04")

    // ksp: the processor has to stay on the runtime classpath as well. ProGuard's release step
    // (packageRelease*) rejects the @ComposablesGalleryItem references as unresolved otherwise;
    // making it compileOnly requires a -dontwarn rule in a custom ProGuard configuration file.
    implementation("com.google.devtools.ksp:symbol-processing-api:2.1.20-2.0.0")
    implementation(project(":processor"))
    ksp(project(":processor"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

compose.desktop {
    application {
        mainClass = "net.systemvi.configurator.MainKt"

        nativeDistributions {
            when {
                isWindows -> targetFormats(TargetFormat.Exe, TargetFormat.Msi)
                isMacOs -> targetFormats(TargetFormat.Dmg, TargetFormat.Pkg)
                else -> targetFormats(TargetFormat.Deb, TargetFormat.Rpm)
            }

            packageName = "KeyboardConfigurator"
            packageVersion = releaseVersion
            vendor = "SystemVi"
            description = "Configurator for custom 3D printed keyboards"
            copyright = "(c) 2026 SystemVi"
            // modules("jdk.unsupported") // add only if `suggestModules` or the packaged app asks for it

            windows {
                menuGroup = "KeyboardConfigurator"
                perUserInstall = true // installs into the user profile, so no admin rights are needed
                // Must stay constant for the lifetime of the app, otherwise updates install side by side.
                upgradeUuid = "8a1cd2f4-6b1e-4f0a-9c3d-2e7b5a4c1d90"
                // iconFile.set(project.file("packaging/icon.ico"))
            }

            linux {
                // TODO: replace with a real maintainer address before publishing a .deb
                debMaintainer = "maintainer@example.com"
                menuGroup = "Utility"
                appCategory = "Utility"
                appRelease = "1"
                // iconFile.set(project.file("packaging/icon.png"))
            }

            macOS {
                bundleID = "net.systemvi.keyboardconfigurator"
                // iconFile.set(project.file("packaging/icon.icns"))
            }
        }
    }
}
