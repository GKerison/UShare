# UShare
> A share library for android by UMeng

[![Release](https://jitpack.io/v/GKerison/UShare.svg?style=flat-square)](https://jitpack.io/#GKerison/UShare)
## 项目简介
为进一步简化Android项目的分享，UShare基于友盟分享进一步简化，移除额外的支持平台，移除分享UI的代码，简化分享配置和使用的API，目前支持常用的QQ，微信，新浪相关的图文和网页分享。

## 分享集成
1. 在根目录下build.gradle中增加gitpack仓库
```
allprojects {
    repositories {
      ...
      maven { url "https://jitpack.io" }
    }
}
```
2. 在项目的build.gradle中添加UShare的依赖
```
dependencies {
    compile 'com.github.GKerison:UShare:v1.0.0'
}
```

3. 在项目的AndroidManifest.xml中增加友盟key和QQ认证的Activity
```
  <application>
    ...
    <meta-data
      android:name="UMENG_APPKEY"
      android:value="[友盟 AppKey]" >
    </meta-data>
  </application>
```
4. 【可选】QQ分享需
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
5. 【可选】微信分享需要在项目的包下建立wxapi包和WXEntryActivity
  ```
  public class WXEntryActivity extends WXCallbackActivity {
  }
  ```
6. 项目使用分享前中配置需要使用的分享平台（最好启动页或者Application中）
```
new UShareHelper.Builder(Context context)
                .configQQ(String appId, String appKey)
                .configSina(String appKey, String appSecret, String redirectUrl)
                .configWeiXin(String appKey, String appSecret)
                .build();
```

7. 使用分享
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

## 分享支持
  - QQ
  - QQ空间
  - 微信
  - 朋友圈
  - 新浪

## 附录
- 注意事项和申请Key流程可参考官方文档
- 此封装基于友盟最新6.04精简版SDK
- 友盟Android分享集成文档相关[传送门](http://dev.umeng.com/social/android/quick-integration)
