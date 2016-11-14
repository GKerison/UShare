package cn.kerison.ushare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.Map;

/**
 * Created by k on 2016/11/7.
 */

public class UShareHelper {

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 配置微信
         *
         * @param appKey
         * @param appSecret
         * @return
         */
        public Builder configWeiXin(String appKey, String appSecret) {
            PlatformConfig.setWeixin(appKey, appSecret);
            return this;
        }

        /**
         * 配置新浪 注意友盟的回调默认为http://sns.whalecloud.com/sina2/callback，在新浪后台配置
         *
         * @param appKey
         * @param appSecret
         * @return
         */
        public Builder configSina(String appKey, String appSecret) {
            PlatformConfig.setSinaWeibo(appKey, appSecret);
            Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
            return this;
        }

        /**
         * 配置QQ
         *
         * @param appId
         * @param appKey
         * @return
         */
        public Builder configQQ(String appId, String appKey) {
            PlatformConfig.setQQZone(appId, appKey);
            return this;
        }

        /**
         * 配置默认的Toast提示（部分版本中不会显示，这里主要是方便某些应用有自己的提示，防止有多次提醒的状况）
         *
         * @param showToast
         * @return
         */
        public Builder configToast(boolean showToast) {
            Config.IsToastTip = showToast;
            return this;
        }

        /**
         * 配置友盟的Log
         *
         * @param isDebug
         * @return
         */
        public Builder configDebug(boolean isDebug) {
            Log.LOG = isDebug;
            return this;
        }

        /**
         * 配置是否显示默认的弹出框（需要的的话，完全可以自己在代码中实现）
         *
         * @param showDialog
         * @return
         */
        public Builder configDialog(boolean showDialog) {
            Config.dialogSwitch = showDialog;
            return this;
        }

        /**
         * 应用配置
         */
        public void build() {
            UMShareAPI.get(this.context);
            this.context = null;
        }
    }

    /**
     * 回调拦截
     *
     * @param context
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

    public static void share(Activity activity, SHARE_MEDIA platform, String title, String desc, String image, String url, UMShareListener shareListener) {
        share(activity, platform, title, desc, new UMImage(activity, image), url, shareListener);
    }

    /**
     * 分享
     *
     * @param activity
     * @param platform
     * @param title
     * @param desc
     * @param image
     * @param url
     * @param shareListener
     */
    public static void share(Activity activity, SHARE_MEDIA platform, String title, String desc, UMImage image, String url, UMShareListener shareListener) {
        new ShareAction(activity)
                .setPlatform(platform)
                .withTitle(title)
                .withText(desc)
                .withMedia(image)
                .withTargetUrl(url)
                .setCallback(shareListener)
                .share();

    }

    /**
     * 认证授权，回调包含基本信息
     *
     * @param activity
     * @param platform
     * @param authListener
     */
    public static void auth(Activity activity, SHARE_MEDIA platform, UMAuthListener authListener) {
        UMShareAPI.get(activity).doOauthVerify(activity, platform, authListener);
    }

    /**
     * 认证授权后，可以获得用户的详细信息
     *
     * @param activity
     * @param platform
     * @param authListener
     */
    public static void getUserInfo(Activity activity, SHARE_MEDIA platform, UMAuthListener authListener) {
        UMShareAPI.get(activity).getPlatformInfo(activity, platform, authListener);
    }

    /**
     * 认证并且获取用户信息 相当于 auth && getUserInfo
     *
     * @param activity
     * @param platform
     * @param authListener
     */
    public static void authAndGetInfo(final Activity activity, final SHARE_MEDIA platform, final UMAuthListener authListener) {
        UMShareAPI.get(activity).doOauthVerify(activity, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(activity).getPlatformInfo(activity, platform, authListener);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (authListener != null) {
                    authListener.onError(share_media, i, throwable);
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                if (authListener != null) {
                    authListener.onCancel(share_media, i);
                }
            }
        });
    }
}
