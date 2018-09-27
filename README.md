# 萝卜丝·云客服

- 致力于提供强大、稳定、灵活、可扩展、定制化的客户服务一站式平台
- [官方网站](https://www.bytedesk.com)
- [开发文档](https://www.bytedesk.com/support/article?uid=201808221551193&aid=201808252118181)

## 准备工作

- [注册账号](https://www.bytedesk.com/admin#/register)
- [登录后台](https://www.bytedesk.com/admin#/login)
- 分配应用：登录后台->所有设置->应用管理->APP

## 集成SDK

萝卜丝·云客服在经典版[微客服](http://www.weikefu.net)基础上面做了重构，将原先一个SDK一分为二为两个sdk：

- 核心库：com.bytedesk:core: [![Download](https://api.bintray.com/packages/jackning/maven/core/images/download.svg)](https://bintray.com/jackning/maven/core/_latestVersion), [详情](https://bintray.com/jackning/maven/core)
- 界面库，完全开源(Demo中的bytedesk-ui模块)，方便开发者自定义界面：com.bytedesk:ui: [![Download](https://api.bintray.com/packages/jackning/maven/ui/images/download.svg)](https://bintray.com/jackning/maven/ui/_latestVersion), [详情](https://bintray.com/jackning/maven/ui)

开发环境：

- Android Studio 3.1
- Gradle 4.6

> 第一步：在项目build.gradle的 allprojects -> repositories 添加

```java
maven {
    url  "https://dl.bintray.com/jackning/maven"
}
```

> 修改完后，效果如下：

```java
allprojects {
    repositories {
        jcenter()
        google()
        maven {
            url  "https://dl.bintray.com/jackning/maven"
        }
    }
}
```

> 第二步：在module的build.gradle dependencies 里面添加依赖库，两种方法二选其一：

> 方法一：基于默认UI

```java
// 加载核心库
implementation 'com.bytedesk:core:1.0.1' // 注意：1.0.1为编写文档时的版本，集成到项目时请使用最新版
// 加载默认UI库
implementation 'com.bytedesk:ui:1.0.0' // 注意：1.0.0为编写文档时的版本，集成到项目时请使用最新版
```

> 方法二：自定义对话界面UI

```java
// 加载核心库
implementation 'com.bytedesk:core:1.0.1'
// 首先将工程中的bytedesk-ui module加入自己项目
implementation project(':bytedesk-ui')
```

## AndroidManifest.xml

> 添加权限

```java
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
```

> 其他

```java
<!--萝卜丝bytedesk.com代码 开始-->
<activity
    android:name="com.bytedesk.ui.activity.ChatActivity"
    android:screenOrientation="portrait"/>

<service android:name="com.bytedesk.paho.android.service.MqttService"/>
<!--./萝卜丝bytedesk.com代码 结束-->
```

## 登录接口

- 获取appkey：登录后台->所有设置->应用管理->APP->appkey列
- 获取subDomain，也即企业号：登录后台->所有设置->客服账号->企业号

> 登录接口，默认用户名登录，系统自动生成一串数字作为用户名，其中appkey和企业号需要替换为真实值

```java
// 需要填写真实的
final String appkey = "201809171553111";
final String subdomain = "vip";
// 登录接口
BDCoreApi.visitorLogin(getApplicationContext(), appKey, subDomain, new LoginCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        //
        try {
            Logger.d("login success message: " + object.get("message") + " status_code:" + object.get("status_code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(JSONObject object) {
        try {
            Logger.d("login failed message: " + object.get("message")
                    + " status_code:" + object.get("status_code")
                    + " data:" + object.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
});
```

## 开始会话

- 获取uId: 登录后台->所有设置->客服账号->超级管理员用户的：唯一ID(uId)
- 获取wId: 登录后台->所有设置->工作组->超级管理员用户的：唯一ID(wId)

```java
BDUiApi.visitorStartChatActivity(getContext(), uId, wId, '默认标题');
```

## 用户信息接口 (可选)

总共有三个相关接口：

- 获取用户信息接口：获取用户昵称，以及key/value信息对
- 设置用户昵称接口：设置用户昵称，可在客服端显示
- 设置用户任意信息接口：自定义key/value设置用户信息，可在客服端显示查看

> 获取用户信息 (可选)

```java
BDCoreApi.visitorGetUserInfo(getContext(), new BaseCallback() {

    @Override
    public void onSuccess(JSONObject object) {
        try {
            Logger.d("get userinfo success message: " + object.get("message")+ " status_code:" + object.get("status_code")+ " data:" + object.get("data"));
            //
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(JSONObject object) {
        try {
            Logger.d("get userinfo failed message: " + object.get("message")
                    + " status_code:" + object.get("status_code")
                    + " data:" + object.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
});
```

> 设置用户昵称接口 (可选)

```java
// 调用接口设置昵称
BDCoreApi.visitorSetNickname(getContext(), "nickname", new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 设置昵称成功
    }

    @Override
    public void onError(JSONObject object) {
        // "设置昵称失败"
    }
});
```

> 设置用户任意信息接口 (可选)

```java
BDCoreApi.visitorSetUserinfo(getContext(), "自定义key", "自定义value", new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 设置标签成功
    }

    @Override
    public void onError(JSONObject object) {
        // 设置自定义标签失败
    }
});
```

## 在线状态接口 (可选)

提供两个接口：

- 查询某个工作组id的在线状态
- 查询某个客服uId的在线状态（获取uId: 登录后台->所有设置->客服账号->客服：唯一ID(uId)）

> 获取某个工作组的在线状态：1代表在线，注意需要替换真实参数

```java
BDCoreApi.visitorGetWorkgroupStatus(getContext(), mDefaultWorkgroupId, new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        //  获取成功
        try {
            // String workgroup_id = object.getJSONObject("data").getString("workgroup_id");
            // String status = object.getJSONObject("data").getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(JSONObject object) {
        // 获取失败
    }
});
```

> 获取某个客服uId的在线状态：注意需要替换真实参数

```java
BDCoreApi.visitorGetAgentStatus(getContext(), mDefaultAgentname, new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 获取成功
        try {
            // String agent = object.getJSONObject("data").getString("agent");
            // String status = object.getJSONObject("data").getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(JSONObject object) {
        // 获取失败
    }
});
```

## 历史会话接口 (可选)

> 支持获取用户的所有历史会话

```java
BDCoreApi.visitorGetThreads(getContext(), new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 获取成功
    }

    @Override
    public void onError(JSONObject object) {
        // 获取失败
    }
});
```
