# 萝卜丝·云客服

- 致力于提供强大、稳定、灵活、可扩展、定制化的客户服务一站式平台
- [开发文档](https://www.bytedesk.com/support/article?uid=201808221551193&aid=201808252118181)

## 准备工作

- <a href="https://www.bytedesk.com/admin#/register" target="_blank">注册账号</a>
- <a href="https://www.bytedesk.com/admin#/login" target="_blank">登录后台</a>
- 分配应用：登录后台->接入设置->移动应用->添加应用

## 集成SDK

萝卜丝·云客服在经典版<a href="http://www.weikefu.net" target="_blank">微客服</a>基础上面做了重构，将原先一个SDK一分为二为两个sdk：

  - 核心库：com.bytedesk:core: [![Download](https://api.bintray.com/packages/jackning/maven/core/images/download.svg)](https://bintray.com/jackning/maven/core/_latestVersion)
  - 界面库，完全开源(Demo中的bytedesk-ui模块)，方便开发者自定义界面：com.bytedesk:ui: [![Download](https://api.bintray.com/packages/jackning/maven/ui/images/download.svg)](https://bintray.com/jackning/maven/ui/_latestVersion)


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
implementation 'com.bytedesk:core:1.0.1' // 注意：1.0.1为编写文档时的版本，集成到项目时请使用最新版，
// 加载默认UI库
implementation 'com.bytedesk:ui:1.0.0'
```


> 方法二：自定义对话界面UI

```java
// 加载核心库
implementation 'com.bytedesk:core:1.0.1'
// 首先将工程中的bytedesk-ui module加入自己项目
implementation project(':bytedesk-ui')
```

## 登录接口

基于两点考虑：

  - 方便开发者快速集成
  - 方便开发者对接App自有用户系统

我们开放了两种登录接口, 二选其一

`获取Appkey和Subdomain：登录后台->接入设置->移动应用`

> 接口一：默认用户名登录，系统自动生成一串数字作为用户名

```java
// 需要填写真实的：登录后台->接入设置->移动应用 页面获取
final String appkey = "appkey";
final String subdomain = "user1";
// 登录接口
WXCoreApi.visitorLogin(getApplicationContext(), appkey, subdomain, new LoginCallback() {

    @Override
    public void onSuccess(JSONObject object) {
        // 登录成功
        try {
            Logger.d("login success message: " + object.get("message") + " status_code:" + object.get("status_code"));
            // PassportToken passportToken = (PassportToken) object.get("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(JSONObject object) {
        // 登录失败
        try {
            Logger.d("login faile message: " + object.get("message")+ " status_code:" + object.get("status_code")+ " data:" + object.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
});
```

> 接口二：自定义用户名登录

> 首先调用注册接口：

```java
WXCoreApi.visitorRegister("username", "nickname", appkey, subdomain, new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 注册成功
        // 在此调用自定义用户名登录接口
    }

    @Override
    public void onError(JSONObject object) {
       // 注册失败
    }
});
```

> 其次调用登录接口：

```java
WXCoreApi.visitorLogin(getApplicationContext(),"username", appkey, subdomain, new LoginCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 登录成功
    }

    @Override
    public void onError(JSONObject object) {
        // 登录失败
    }
});
```

## 用户信息接口

总共有三个相关接口：

 - 获取用户信息接口：获取用户昵称，以及key/value信息对
 - 设置用户昵称接口：设置用户昵称，可在客服端显示
 - 设置用户任意信息接口：自定义key/value设置用户信息，可在客服端显示查看

> 获取用户信息

```java
WXCoreApi.visitorGetUserinfo(getContext(), new BaseCallback() {

    @Override
    public void onSuccess(JSONObject object) {
        try {
            Logger.d("get userinfo success message: " + object.get("message")+ " status_code:" + object.get("status_code")+ " data:" + object.get("data"));
            //
            Userinfo userinfo = (Userinfo) object.get("data");
            // 获取昵称
            // userinfo.getNickname();
            // 获取其他用户key:value
            for (int i = 0; i < userinfo.getTags().size(); i++) {
                Logger.i("key:" + userinfo.getTags().get(i).getKey() + " value:" + userinfo.getTags().get(i).getValue());
                if (userinfo.getTags().get(i).getKey().equals(mTagkey)) {
                    // 获取并设置tag
                    // mTagValue = userinfo.getTags().get(i).getValue();
                }
            }
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
            // WXUIUtils.showTipDialog(getContext(), "获取个人资料失败");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
});
```

> 设置用户昵称接口

```java
// 调用接口设置昵称
WXCoreApi.visitorSetNickname(getContext(), "nickname", new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 设置昵称成功
        //解析 object
        // try {
        //     String nickname = object.getJSONObject("data").getString("nickname");
        // } catch (JSONException e) {
        //     e.printStackTrace();
        // }
    }

    @Override
    public void onError(JSONObject object) {
        // "设置昵称失败"
    }
});
```


> 设置用户任意信息接口 

```java
WXCoreApi.visitorSetUserinfo(getContext(), "自定义key", "自定义value", new BaseCallback() {
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


## 在线状态接口

提供两个接口：

  - 查询某个客服账号的在线状态
  - 查询某个工作组id的在线状态


> 获取某个客服账号的在线状态：online代表在线，offline代表离线

```java
WXCoreApi.visitorGetAgentStatus(getContext(), mDefaultAgentname, new BaseCallback() {
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

> 获取某个工作组的在线状态：online代表在线，offline代表离线

```java
WXCoreApi.visitorGetWorkgroupStatus(getContext(), mDefaultWorkgroupId, new BaseCallback() {
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


## 历史会话接口


支持获取用户的所有历史会话

```java
WXCoreApi.visitorGetThreads(getContext(), new BaseCallback() {
    @Override
    public void onSuccess(JSONObject object) {
        // 获取成功
        try {
            // VisitorGetThreadsResult visitorGetThreadsResult = (VisitorGetThreadsResult) object.get("data");
            // Logger.d(visitorGetThreadsResult.getMessage());
            // visitorGetThreadsResult.getThreadList();
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

