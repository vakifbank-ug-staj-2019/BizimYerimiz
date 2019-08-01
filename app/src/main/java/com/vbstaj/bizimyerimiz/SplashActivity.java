package com.vbstaj.bizimyerimiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;

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
                2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}