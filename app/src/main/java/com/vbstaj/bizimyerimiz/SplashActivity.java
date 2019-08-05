package com.vbstaj.bizimyerimiz;

import android.content.Intent;

import com.vbstaj.bizimyerimiz.utils.Utils;

public class SplashActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

        if(Utils.fbaseAuth.getCurrentUser() != null){
            getLoginUser(Utils.fbaseAuth.getCurrentUser().getUid());
        }
        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(SplashActivity.this, CommentActivity.class);
                    startActivity(intent);
                },
                2500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}