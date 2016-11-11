package cn.kerison.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.kerison.ushare.UShareHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UShareHelper.Builder(this)
                .configQQ("1103422385", "fVOoRq2llHLt9UZS")
//                .configSina("", "", "")
//                .configWeiXin("", "")
                .build();

        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareAction();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UShareHelper.onActivityResult(this, requestCode, resultCode, data);
    }

    private void onShareAction(){
        UShareHelper.share(MainActivity.this, SHARE_MEDIA.QQ, "TITLE", "DESC", "https://dn-coding-net-production-static.qbox.me/2f356c76-a6a9-41cf-ba1d-9d21e8c9ec29.jpg", "https://kerison.coding.me", new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(MainActivity.this, "onResult", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(MainActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
