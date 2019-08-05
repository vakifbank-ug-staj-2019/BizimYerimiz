package com.vbstaj.bizimyerimiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.vbstaj.bizimyerimiz.utils.Utils;

public class MainActivity extends BaseActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button register;
    private Button resetPassword;

    @Override
    public int getContentView() {
        return R.layout.activity_main;

    }

    @Override
    public void initView() {

        emailField = findViewById(R.id.emailInput);
        passwordField = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);
        resetPassword = findViewById(R.id.forgotPassword);


        Utils.fbaseAuth = FirebaseAuth.getInstance();

        displayData();

        if(Utils.fbaseAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this,SplashActivity.class));
            finish();
        }

        /**Giriş Yap butonunu çalıştırır.*/
        loginButton.setOnClickListener(view -> {
            String userEmail = emailField.getText().toString();
            String userPassword = passwordField.getText().toString();
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
            } else {
                if (!userEmail.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")) {
                    showMessage("Lütfen geçerli e-posta giriniz.");
                } else {
                    loginFunc(userEmail, userPassword);
                }
            }
        });


        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            finish();
        });

        /**Kayıt ol Butonunu çalıştırır.*/
        register.setOnClickListener(view -> {
            Intent u = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(u);
        });

    }

    /**
     * Giriş yap butonuna tıklandıktan sonra eğer userEmail ve userPassword boş değilse bu fonksiyon çalışacak
     */
    public void loginFunc(String userName, String userPassword) {
        Utils.fbaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        saveinfo();
                        startActivity(new Intent(MainActivity.this,SplashActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Lütfen ilgili alanları doğru doldurunuz", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //email password kaydı
    public void saveinfo() {
        SharedPreferences sharedPref = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", emailField.getText().toString());
        editor.putString("password", passwordField.getText().toString());
        editor.apply();
    }
///kaydedilen email ve passwordu yazdırma

    public void displayData() {
        SharedPreferences sharedPref = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

        String email = sharedPref.getString("email", "");
        String pv = sharedPref.getString("password", "");
        emailField.setText(email);
        passwordField.setText(pv);
    }
}



