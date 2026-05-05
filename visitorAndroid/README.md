# Visitor Android

Visitor Android is the native Jetpack Compose implementation of the Bytedesk visitor demo. It mirrors the same three-tab experience as Visitor UniApp.

## Features

- Native Android visitor demo built with Jetpack Compose Material 3.
- Home tab opens plain chat, goods chat, and order chat scenes.
- Messages tab fetches visitor threads from the online API.
- Profile tab switches between preset demo users.
- Chat content is rendered inside Android WebView.

## Default Online Endpoints

- Chat page: https://cdn.weiyuai.cn
- API base: https://api.weiyuai.cn

## Main Files

- app/src/main/java/com/bytedesk/visitorandroid/MainActivity.kt: app entry, tab UI, API loading, WebView chat
- app/src/main/AndroidManifest.xml: network permission and web access settings

## Requirements

- Android Studio with Android SDK installed
- JDK 11
- Android SDK path configured through ANDROID_HOME and ANDROID_SDK_ROOT when building from terminal

## How To Run

1. Open frontend/apps/visitorAndroid in Android Studio.
2. Sync Gradle.
3. Run the app on an emulator or device.

For command-line validation you can use:

```bash
cd frontend/apps/visitorAndroid
ANDROID_HOME=/path/to/android/sdk ANDROID_SDK_ROOT=/path/to/android/sdk ./gradlew :app:compileDebugKotlin
```

## How To Use

1. Open Home and tap a scene card to enter chat directly.
2. Open Messages to load visitor threads from the online API.
3. Tap a thread to continue the selected conversation.
4. Open Profile to switch the current demo visitor.

## Notes

- The app uses online endpoints by default.
- INTERNET permission and cleartext traffic support are already configured in AndroidManifest.xml.
