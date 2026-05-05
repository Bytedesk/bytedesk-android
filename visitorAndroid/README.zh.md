# Visitor Android

Visitor Android 是 Bytedesk 访客端的原生 Android 实现，基于 Jetpack Compose，整体交互与 Visitor UniApp 的三个 tab 保持一致。

## 功能说明

- 基于 Jetpack Compose Material 3 的原生访客演示应用。
- 首页支持普通会话、商品会话、订单会话入口。
- 消息页从线上接口拉取访客历史会话。
- 我的页面支持切换预置演示用户。
- 聊天内容通过 Android WebView 加载。

## 默认线上地址

- Chat 页面：https://cdn.weiyuai.cn
- API 地址：https://api.weiyuai.cn

## 关键文件

- app/src/main/java/com/bytedesk/visitorandroid/MainActivity.kt：应用入口、tab 布局、接口加载、聊天 WebView
- app/src/main/AndroidManifest.xml：网络权限与 Web 访问配置

## 环境要求

- 已安装 Android Studio 和 Android SDK
- JDK 11
- 命令行构建时配置 ANDROID_HOME 与 ANDROID_SDK_ROOT

## 使用步骤

1. 用 Android Studio 打开 frontend/apps/visitorAndroid。
2. 等待 Gradle 同步完成。
3. 运行到模拟器或真机。

命令行校验可使用：

```bash
cd frontend/apps/visitorAndroid
ANDROID_HOME=/path/to/android/sdk ANDROID_SDK_ROOT=/path/to/android/sdk ./gradlew :app:compileDebugKotlin
```

## 体验流程

1. 进入首页，点击场景卡片直接进入客服会话。
2. 进入消息页，从线上接口拉取历史会话。
3. 点击某条会话继续沟通。
4. 进入我的页切换当前演示用户。

## 说明

- 当前默认使用线上地址。
- AndroidManifest.xml 中已配置网络权限和 cleartext 支持。
