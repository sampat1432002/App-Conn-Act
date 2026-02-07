# DEVLOG — App-Conn-Act (Simple Chat)

## Project Overview
- **App Name**: Simple Chat
- **Package**: com.example.app1
- **Language**: Java
- **Platform**: Android (min SDK 21 / Android 5.0)
- **Repo**: https://github.com/sampat1432002/App-Conn-Act

## Tech Stack
- Android SDK 34 (compile & target)
- AndroidX AppCompat 1.6.1
- Material Design 1.11.0
- ConstraintLayout 2.1.4
- Gradle 8.2 + AGP 8.2.0

---

## Development Timeline

### Phase 1 — Initial Build (Feb 2026)
- Scaffolded project on mobile with basic chat UI
- RecyclerView with two view types (sent/received bubbles)
- Auto-reply system with 10 canned responses
- Material Design theme with day/night support

### Phase 2 — Code Review & Fixes (Feb 7, 2026)
Reviewed codebase and identified 6 issues across build config, runtime safety, and UI.

#### Fix 1: Upgrade Build System
- **Problem**: AGP 4.2.1 and Gradle 6.7.1 were too outdated for modern Android Studio. jcenter() is a dead repository. junit had a wildcard version.
- **Solution**: Upgraded to AGP 8.2.0, Gradle 8.2, SDK 34. Removed jcenter, pinned all dependency versions. Migrated to namespace-based build config.
- **Commit**: `8a5c7f4`

#### Fix 2: AndroidManifest exported attribute
- **Problem**: Activities with intent-filters need `android:exported="true"` for targetSdk 31+. Without it, the app won't install on Android 12+ devices.
- **Solution**: Added `android:exported="true"` to MainActivity.
- **Commit**: `c656ec0`

#### Fix 3: Handler Memory Leak
- **Problem**: `handler.postDelayed()` schedules auto-reply callbacks but never clears them on Activity destruction. If the user navigates away while a reply is pending, the callback fires on a dead Activity, risking a crash.
- **Solution**: Added `onDestroy()` override that calls `handler.removeCallbacksAndMessages(null)`.
- **Commit**: `6b0f7fe`

#### Fix 4: Dark Mode Color Support
- **Problem**: Background colors were hardcoded (`#F5F5F5`, `@android:color/white`) in layout XML, ignoring the dark theme entirely. Text would be unreadable in night mode.
- **Solution**: Extracted colors to `values/colors.xml` and added dark variants in `values-night/colors.xml`. Updated layout to reference color resources.
- **Commit**: `7a32306`

---

## Challenges & Decisions
1. **AGP 8 migration**: Required moving `package` from AndroidManifest to `namespace` in build.gradle — this is a breaking change in AGP 8.x
2. **minSdk bump 16 → 21**: Dropped pre-Lollipop support since <1% of active devices run Android 4.x and it unlocks modern APIs
3. **Dark mode approach**: Chose color resource overrides (values-night) over programmatic theming for simplicity

---

## Next Steps
- [ ] Test build in Android Studio — verify Gradle sync and APK compilation
- [ ] Run on emulator and physical device
- [ ] Test dark mode toggle
- [ ] Consider adding message persistence (Room/SQLite)
- [ ] Consider real networking (WebSocket/Firebase)
- [ ] Add proper unit tests
