package com.vbstaj.bizimyerimiz;

import android.content.Intent;

public class SplashActivity extends BaseActivity {

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }


    @Override
    public void initView() {

        if(fbaseAuth.getCurrentUser() != null){
            getLoginUser(fbaseAuth.getCurrentUser().getUid());
        }
        new android.os.Handler().postDelayed(
                () -> {
                    Intent intent = new Intent(SplashActivity.this, CommandActivity.class);
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