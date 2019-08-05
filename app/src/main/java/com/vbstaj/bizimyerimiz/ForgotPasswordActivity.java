package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.vbstaj.bizimyerimiz.utils.Utils;

public class ForgotPasswordActivity extends BaseActivity {

    private EditText emailField;
    private Button resetPassword;
    private Button cancel;

    @Override
    public int getContentView() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void initView() {

        Utils.fbaseAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailInput);
        resetPassword = findViewById(R.id.resetButton);
        cancel = findViewById(R.id.cancelButton);

        cancel.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPasswordActivity.this,MainActivity.class));
            finish();
        });

        resetPassword.setOnClickListener(view -> {
            Utils.fbaseAuth.sendPasswordResetEmail(emailField.getText().toString())
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
