package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText emailField;
    private Button resetPassword;
    private Button cancel;


    private FirebaseAuth fbaseAuth;
    private FirebaseUser fbaseUser;

    @Override
    public int getContentView() {
        return R.layout.activity_forgot_password;

    }

    @Override
    public void initView() {

        fbaseAuth = FirebaseAuth.getInstance();
        emailField = (EditText)findViewById(R.id.emailInput);
        resetPassword = (Button)findViewById(R.id.resetButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        cancel.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
            finish();
        });

        resetPassword.setOnClickListener(view -> {
            fbaseAuth.sendPasswordResetEmail(emailField.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showMessage("Şifre sıfırlama bağlantısı yollanmıştır.");
                            startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
                            finish();
                        }else{
                            showMessage("Bir hata oluştu");
                        }
                    });
        });
    }
}
