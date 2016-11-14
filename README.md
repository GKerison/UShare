# UShare
> A share library for android by UMeng

[![Release](https://jitpack.io/v/GKerison/UShare.svg?style=flat-square)](https://jitpack.io/#GKerison/UShare)
## 项目简介
为进一步简化Android项目的分享，UShare基于友盟分享进一步简化，移除额外的支持平台，移除分享UI的代码，简化分享配置和使用的API，目前支持常用的QQ，微信，新浪相关的图文和网页分享，新版支持获取用户授权信息。

【注】友盟精简版有部分问题，新版的将不在使用精简版，请使用最近版本的UShare

## 分享集成

1 在根目录下build.gradle中增加gitpack仓库
```
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
}
```

2 在项目的build.gradle中添加UShare的依赖
```
dependencies {
    compile 'com.github.GKerison:UShare:latest.release'
}
```

3 在项目的AndroidManifest.xml中增加友盟key
```
  <application>
    ...
    <meta-data
      android:name="UMENG_APPKEY"
      android:value="[友盟 AppKey]" >
    </meta-data>
  </application>
```

4 【可选】QQ分享需配置QQ认证的Activity
  ```
    <application>
      ...
      <activity
        android:name="com.umeng.qq.tencent.AuthActivity"
        android:launchMode="singleTask"
        android:noHistory="true" >
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />

            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />

            <data android:scheme="tencent[QQ appID]" />
        </intent-filter>
      </activity>
    </application>
  ```

5 【可选】微信分享需要在项目的包下建立wxapi包和WXEntryActivity
  ```
  import com.umeng.socialize.weixin.view.WXCallbackActivity;
  public class WXEntryActivity extends WXCallbackActivity {
  }
  ```

6 【可选】新浪分享需要在项目的包下建立WBShareActivity
  ```
  import com.umeng.socialize.media.WBShareCallBackActivity;
  public class WBShareActivity extends WBShareCallBackActivity {
  }
  ```

7 项目使用分享前中配置需要使用的分享平台（最好启动页或者Application中）
```
new UShareHelper.Builder(Context context)
                .configQQ(String appId, String appKey)
                .configSina(String appKey, String appSecret)
                .configWeiXin(String appKey, String appSecret)
                .configDebug(BuildConfig.DEBUG)
                .configToast(true)
                .configDialog(false)
                .build();
```

8 使用分享
```
UShareHelper.share(Activity activity, SHARE_MEDIA platform, String title, String desc, String image, String url, UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
              //分享成功
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
              //分享失败
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
              //分享取消
            }
        });
```
9 获取用户信息
```
UShareHelper.authAndGetInfo(Activity activity, SHARE_MEDIA platform, new UMAuthListener() {
           @Override
           public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
              //授权成功
           }

           @Override
           public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
              //授权失败
           }

           @Override
           public void onCancel(SHARE_MEDIA share_media, int i) {
              //授权取消
           }
       });
```

## 分享支持
  - QQ
  - QQ空间
  - 微信
  - 朋友圈
  - 新浪

## 获取用户信息
  - QQ
  - 微信
  - 新浪

## 附录
- 注意事项和申请Key流程可参考官方文档
- 此封装基于友盟最新6.0.5完整版
- 友盟Android分享集成文档相关[传送门](http://dev.umeng.com/social/android/quick-integration)
- 友盟分享问题查询[传送门](http://bbs.umeng.com/forum-social-1.html)
- 分享需要的类和权限都配置在UShare包中的Mainifest中了，新版的AS会自动合并，如果您使用的版本还不支持，手动拷过去就行了
- WBShareActivity放在根包目录下，WXEntryActivity放在根包wxapi下
- 新浪的so库提供的版本较多，最好根据需要配置一下
```
//主工程的build.gradle>android>defaultConfig
ndk {
      // 设置支持的SO库架构
    abiFilters 'armeabi' , 'armeabi-v7a'//, 'x86', 'x86_64', 'arm64-v8a'
}
```
- 新浪的回调地址，注意配置安全域名，默认修改为http://sns.whalecloud.com/sina2/callback
- 应用的签名最好使用UmengTool或者官方的签名工具检查下，避免不要的错误
