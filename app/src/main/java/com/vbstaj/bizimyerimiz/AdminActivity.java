package com.vbstaj.bizimyerimiz;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends BaseActivity {

    private Button geri;

    @Override
    public int getContentView() {
        return R.layout.activity_admin;
    }

    @Override
    public void initView() {


        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this, CommandActivity.class);
                startActivity(i);
                finish();
            }
        });












    }

}
