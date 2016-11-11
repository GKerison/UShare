package cn.kerison.ushare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by k on 2016/11/7.
 */

public class UShareHelper {

    public static class Builder {

        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder configWeiXin(String appKey, String appSecret) {
            PlatformConfig.setWeixin(appKey, appSecret);
            return this;
        }

        public Builder configSina(String appKey, String appSecret, String redirectUrl) {
            PlatformConfig.setSinaWeibo(appKey, appSecret);
            Config.REDIRECT_URL = redirectUrl;
            return this;
        }

        public Builder configQQ(String appId, String appKey) {
            PlatformConfig.setQQZone(appId, appKey);
            return this;
        }

        public void build() {
            UMShareAPI.get(this.context);
            this.context = null;
        }
    }

    public static void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);//新浪、QQ回调处理
    }

    public static void share(Activity activity, SHARE_MEDIA platform, String title, String desc, String image, String url, UMShareListener shareListener){
        share(activity,platform,title,desc,new UMImage(activity,image),url,shareListener);
    }

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
}
