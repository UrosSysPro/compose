import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
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
    implementation(libs.jssc)

    // compose multiplatform: the artifacts are versioned by the compose plugin (libs.versions.toml)
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.compose.dnd)

    // Material icons: JetBrains discontinued this artifact after 1.7.3, see AGENTS.md
    implementation(libs.material.icons.core)

    // logger
    implementation(libs.slf4j.simple)

    // arrow kt
    implementation(libs.arrow.core)
    implementation(libs.arrow.core.serialization)
    implementation(libs.arrow.optics)
    ksp(libs.arrow.optics.ksp)

    // material 3 and colors
    implementation(libs.materialkolor)

    // ksp: the processor has to stay on the runtime classpath as well. ProGuard's release step
    // (packageRelease*) rejects the @ComposablesGalleryItem references as unresolved otherwise;
    // making it compileOnly requires a -dontwarn rule in a custom ProGuard configuration file.
    implementation(libs.ksp.api)
    implementation(project(":processor"))
    ksp(project(":processor"))

    testImplementation(libs.kotlin.test)
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
                upgradeUuid = "b227d66f-c882-4d9d-b30e-a8995ca6cebc"
                // iconFile.set(project.file("packaging/icon.ico"))
            }

            linux {
                // TODO: replace with a real maintainer address before publishing a .deb
                debMaintainer = "karaleicu@gmail.com"
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
