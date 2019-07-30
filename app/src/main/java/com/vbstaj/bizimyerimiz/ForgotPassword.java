package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends BaseActivity {

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
            startActivity(new Intent(ForgotPassword.this,MainActivity.class));
            finish();
        });

        resetPassword.setOnClickListener(view -> {
            fbaseAuth.sendPasswordResetEmail(emailField.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showMessage("Şifre sıfırlama bağlantısı yollanmıştır.");
                            startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                            finish();
                        }else{
                            showMessage("Bir hata oluştu");
                        }
                    });
        });
    }
}
