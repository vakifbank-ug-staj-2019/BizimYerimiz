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
        }else{
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
        }

        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(SplashActivity.this, CommentActivity.class);
                    startActivity(intent);
                },
                3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}