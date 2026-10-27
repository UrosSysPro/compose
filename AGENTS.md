# AGENTS.md

Guide for AI coding agents (and humans) working in this repository. Read this before touching code.
Keep it up to date when you change build setup, packaging, or the serial protocol.

## What this project is

**KeyboardConfigurator** — a desktop app for designing and configuring custom 3D-printed keyboards.
It is a Kotlin/JVM **Compose Multiplatform** application with four functional areas:

1. **Design** — lay out a keyboard (rows, keycaps, widths/heights/padding) and edit the JSON model.
2. **Configure** — assign keys/macros/layers/snap-tap pairs to a keymap and upload it to firmware.
3. **Tester** — visual key tester with sound, driven by key events reported over serial.
4. **Settings / Gallery** — theme, and a KSP-generated gallery of annotated composables.

It talks to keyboard firmware over a serial (UART) link via **jssc**.

### Scope guardrails

- **Targets: Windows, macOS, Linux desktops only.** Mobile (Android/iOS) and web (Wasm/JS) are out of
  scope. Do not add KMP mobile/web targets, expect/actual for them, or browser-only libraries.
- Single-window desktop app. There is no server, no database, no network API.
- The distributed app must stay **self-contained** (bundled JDK runtime, no Java required on the user machine).

## Stack and versions

| Concern | Version / choice |
|---|---|
| Language | Kotlin 2.1.20 (`kotlin("jvm")`) |
| UI | Compose Multiplatform 1.8.1 + Material 3 (`compose.material3`) |
| Compose compiler | `org.jetbrains.kotlin.plugin.compose` (must equal the Kotlin version) |
| Build | Gradle wrapper **9.6.0**, Kotlin DSL |
| Codegen | KSP `2.1.20-2.0.0` (KSP version is `<kotlin>-<ksp>`; it must match Kotlin exactly) |
| Domain helpers | Arrow Core / Optics / Optics-KSP 2.1.x, kotlinx-serialization-json 1.8.1 |
| Lifecycle | `org.jetbrains.androidx.lifecycle` ViewModel + runtime-compose 2.9.x |
| Drag & drop | `com.mohamedrejeb.dnd:compose-dnd:0.3.0` |
| Theming | `com.materialkolor:material-kolor:3.0.0-alpha04` (dynamic Material 3 scheme from a seed color) |
| Serial | `io.github.java-native:jssc:2.9.6` |
| Logging | SLF4J + `slf4j-simple` |
| JDK | 21 locally; jpackage requires **JDK 17+** |

Versions are currently declared in **three** places (`gradle.properties`, `settings.gradle.kts`,
`build.gradle.kts`). Treat `build.gradle.kts` as the effective source of truth until it is consolidated.
Do not bump Compose/Kotlin/KSP independently of each other.

## Repository map

```
build.gradle.kts              app module (Compose desktop, KSP, packaging DSL)
settings.gradle.kts           pluginManagement versions, includes :processor
gradle.properties             kotlin.version / compose.version / JVM args
processor/                    KSP symbol processor (annotation -> generated registry)
src/main/kotlin/net/systemvi/configurator/
  Main.kt                     entry point (main = net.systemvi.configurator.MainKt), Window
  App.kt                      root composable: services, MaterialTheme, page routing
  page/                       Page sealed class + one file per page
  components/                 UI; one composable per file, folder = package
    common/                   title bar, nav bar, dialogs, keycaps, pickers, gallery
    configure/ design/ tester/ neo_configure/ settings/ ...
  model/                      domain types (@Serializable, Arrow optics): KeyMap, Keycap, Macro, ...
  data/                       hardcoded key tables and default keymaps
  utils/api/                  KeymapApi (persistence + upload), KeyboardSerialApi (protocol)
  utils/services/             lifecycle "services" shared through the ViewModel store
  utils/export/               STL/3D export drafts (mostly disabled/commented out)
  utils/annotations/          @ComposablesGalleryItem annotation
```

## Commands

```bash
# run the app (dev)
./gradlew run                 # Windows: .\gradlew.bat run

# compile / codegen only
./gradlew build               # includes kspKotlin; the generated registry must exist

# packaged app image (no installer) — smoke test this, not just `run`
./gradlew createReleaseDistributable
./gradlew runReleaseDistributable

# installers — only the formats of the host OS can be built
./gradlew packageReleaseExe packageReleaseMsi     # Windows (needs WiX 3.x; the plugin auto-provisions it)
./gradlew packageReleaseDeb packageReleaseRpm     # Linux  (needs fakeroot / rpm-build)
```

Outputs: `build/compose/binaries/main-release/<format>/`.
If `galleryComponents` fails to resolve, the KSP step did not run — run `./gradlew kspKotlin` (or `clean`)
rather than adding a manual registration.

**The Gradle wrapper scripts are currently not committed** (`.gitignore` ignores `gradle/`, `gradlew`,
`gradlew.bat`). If `./gradlew` is missing in your clone, use a local Gradle 9.6.0 or run
`gradle wrapper --gradle-version 9.6.0 --distribution-type bin` and commit the wrapper.

## Architecture conventions

- **MVVM-lite with Compose state.** ViewModels live in `utils/services` / `components/*ViewModel.kt`
  and extend `androidx.lifecycle.ViewModel`; state is `mutableStateOf` / `by mutableStateOf`.
  Compose with `viewModel { FooService() }`. The same instance is shared per window because the key is
  the class name and the store owner is the `Window` — every composable may safely call `viewModel { ... }`
  for the same type.
- **Services are started/stopped explicitly** from `DisposableEffect` in `App.kt` (`onStart`/`onStop`).
  Persistence happens in `onStop`. New shared services must follow the same pattern and be registered there.
- **Domain modelling uses Arrow.** `Option` for "maybe" values (instead of `null` where it models absence),
  `Either<Macro, Key>` for keymap layers, `@optics` on model data classes. Generated optics come from KSP;
  do not hand-write them.
- **Serialization**: kotlinx-serialization for everything persisted (`Page`, `AppConfig`, `KeyMap`, `Macro`).
  `KeyMap`/`Macro` are encoded with `Json { serializersModule = ArrowModule }` — keep Arrow's module in sync
  if you add Arrow types to a `@Serializable` class.
- **Navigation**: `Page` is a `@Serializable sealed class` of singleton objects. Adding a page means:
  new object in `page/Page.kt` -> branch in the `when` in `App.kt` -> link in `NavBar.kt`. The `when` is
  exhaustive; the compiler will point at the missing branch.
- **UI conventions**: Material 3 only, colors from `MaterialTheme.colorScheme` (never hardcoded colors),
  8/16/32 dp spacing scale, custom icons in `components/common/icons` are preferred over Material icons.
  The main window is rendered with a custom title bar (`TitleBar.kt`), so avoid assuming OS window chrome.
- **KSP gallery**: annotate any `@Composable` with `@ComposablesGalleryItem("Readable Name")` and it is
  registered automatically in `ComposablesGalleryPage`. Never edit `GeneratedComposableRegistry.kt`
  (generated into `build/generated/ksp/...`).
- **Style**: 4-space indent, no wildcard imports, file name == main composable/type, English identifiers,
  no license headers, keep diffs focused (no reformatting of unrelated files).

## Persistence and files

| File | Written by | Content |
|---|---|---|
| `config.json` | `AppStateService.onStop()` | theme seed color, dark mode, last page |
| `keymaps.json` | `KeymapService.onStop()` / `KeymapApi.saveToDisk()` | saved `KeyMap` list (Arrow-serialized) |

Both are opened as **bare relative paths**, i.e. resolved against the process working directory.
That is a known defect for packaged builds (the CWD is not the install dir and may be unwritable).
Do not add new file I/O that assumes CWD; when fixing this, introduce a single helper that resolves an
app-data directory (`%APPDATA%` / `~/Library/Application Support` / `$XDG_CONFIG_HOME`).

`keymaps.json` and `config.json` at the repo root are local runtime state and are git-ignored — never commit them.

## Serial protocol (contract with firmware)

`KeyboardSerialApi` is the single place that speaks to the device. Port scanning uses
`SerialPortList.getPortNames()`; connection is **9600 baud, 8N1**.

App -> device (single bytes/commands):

| Byte | Meaning |
|---|---|
| `l` | set key on layer: `l`, column, row, layer, keycode |
| `m` | set macro on layer: `m`, column, row, layer, actionCount, (keycode, action)*, nameLen, name... |
| `A` / `S` | add / remove layer-key position |
| `E` / `D` | add / remove snap-tap pair |
| `r` / `n` | request keymap / request device name |
| `e` / `d` | enable / disable key press events |
| `R` | delete keymap |
| `f` / `F` / `q` | store to flash / load from flash / format flash |

Device -> app: messages are terminated by `@`; the first byte selects the handler
(`l` keymap, `p` key press, `r` key release, `n` name). Parsing is a byte buffer (`checkForCommands`).

**Do not change opcodes or field order** without coordinating with the firmware repository — this is a
wire format. Add new commands instead of repurposing existing bytes. Keep parsing tolerant: a malformed
frame must not crash the UI thread.

## Packaging and release

jpackage **cannot cross-compile**: each installer must be built on its own OS
(<https://docs.oracle.com/en/java/javase/21/jpackage/packaging-overview.html>). Windows produces
`.exe`/`.msi` (WiX 3.x required), Linux produces `.deb`/`.rpm` (`fakeroot` on Ubuntu, `rpm-build` on
RHEL). macOS (`.dmg`/`.pkg`) must be built on macOS. Formats listed in `targetFormats(...)` that do not
match the host are skipped.

Current packaging state (must be fixed before a real release):

- `packageName = "compose"` and `rootProject.name = "compose"` — installer is called `compose-1.0.0.exe`.
  Renaming later changes the Windows upgrade identity, so decide the final name before the first public release.
- `packageVersion = "1.0.0"` is hardcoded while `version = "1.0-SNAPSHOT"`. MSI/EXE require
  `MAJOR.MINOR.BUILD` (numeric, <= 255.255.65535), DEB requires a digit-leading version — derive both from
  a single version property and sanitize.
- Missing: `vendor`, `description`, `copyright`, `licenseFile`, per-OS `iconFile`, `windows.upgradeUuid`
  (constant, otherwise upgrades reinstall side-by-side), `linux.debMaintainer` / `menuGroup` / `appCategory`.
- `includeAllModules` / `modules(...)` are not configured. After packaging, smoke-test with
  `runReleaseDistributable` and optionally `./gradlew suggestModules`; if a class is missing at runtime,
  add the module (do not blanket-enable all modules without checking the size impact).

Release artifacts worth shipping per OS: installer (`packageReleaseExe`/`packageReleaseMsi`,
`packageReleaseDeb`) plus a portable `createReleaseDistributable` zip/tar.gz for Linux users whose distro
is neither Debian- nor RPM-based.

## Known issues and landmines

1. **Wrapper not in VCS** — see Commands.
2. **CWD-relative persistence** — see Persistence.
3. **Material icons artifact is frozen at 1.7.3.** JetBrains stopped publishing
   `org.jetbrains.compose.material:material-icons-core` after 1.7.3, so with Compose 1.8.1 you get a
   version skew (it also pulls JetBrains `ui` 1.7.3). Only ~11 icons are used (`Add`, `Close`, `Delete`,
   `Edit`, `Check`, `Settings`, `Warning`, `Refresh`, `MoreVert`, `KeyboardArrowUp/Down`). The clean fix is
   to vendor those `ImageVector`s next to the existing custom icons and drop the dependency.
4. **`buildscript { classpath(kotlin("gradle-plugin", ...)) }` is redundant** with the `plugins {}` block;
   the `com.gradleup.shadow` plugin is applied but unused; `implementation(project(":processor"))` ships the
   KSP processor in the app runtime. All three should be removed (keep only `ksp(project(":processor"))`).
5. **No tests exist.** The most valuable first tests are `KeyMap` JSON round-trips and
   serial frame encode/decode (`KeyboardSerialApi`), which are pure functions over byte lists.
6. **Custom window decoration is half-implemented**: `useClientDecoration` is local state and the
   `remember`ed `WindowDecoration` never changes after first composition, so the title-bar toggle is dead
   code. Leave it alone unless the task is explicitly about it.
7. **Linux serial permissions**: the user must be in `dialout` (Debian/Ubuntu) or have a udev rule for the
   device. Document this in user-facing release notes; never try to `chmod` from the app.
8. **JSSC extracts native libs to `java.io.tmpdir`** — fails on `noexec` `/tmp`. Report a clear error instead
   of a stack trace if you touch connection handling.

## Working agreement for agents

- Read the surrounding code before editing; match its style, do not "modernize" files you were not asked about.
- Keep changes scoped to the request; do not mix refactors with features.
- Verify UI changes by running the app (`./gradlew run`) and clicking through the affected page; there are no
  automated UI tests.
- Before finishing: `./gradlew build` must pass, KSP must have run, and packaging DSL changes must be checked
  with `createReleaseDistributable` + `runReleaseDistributable`.
- Ask before: changing the serial protocol, the persisted JSON schema, the package name/vendor/upgrade UUID,
  the Kotlin/Compose/KSP versions, or deleting files outside `build/`.
- Never commit: `build/`, `.gradle/`, `.kotlin/`, `.idea/`, `config.json`, `keymaps.json`, generated KSP sources.
- Release notes and user-facing docs must name the supported platforms explicitly (Windows, macOS, Linux).
