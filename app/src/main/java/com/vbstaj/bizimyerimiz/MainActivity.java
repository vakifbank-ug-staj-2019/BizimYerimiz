package com.vbstaj.bizimyerimiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.vbstaj.bizimyerimiz.model.User;

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

        emailField = (EditText) findViewById(R.id.emailInput);
        passwordField = (EditText) findViewById(R.id.passwordInput);
        loginButton = (Button) findViewById(R.id.loginButton);
        register = (Button) findViewById(R.id.registerButton);
        resetPassword = (Button) findViewById(R.id.forgotPassword);


        fbaseAuth = FirebaseAuth.getInstance();
        fbaseUser = fbaseAuth.getCurrentUser();

        if (fbaseUser != null) { // check user session
            getLoginUser(fbaseAuth.getCurrentUser().getUid());
        }
        displayData();
        /**Giriş Yap butonunu çalıştırır.*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });


        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            finish();
        });

        /**Kayıt ol Butonunu çalıştırır.*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u = new Intent(MainActivity.this, createaccountActivity.class);
                startActivity(u);
            }
        });

    }

    /**
     * Giriş yap butonuna tıklandıktan sonra eğer userEmail ve userPassword boş değilse bu fonksiyon çalışacak
     */
    public void loginFunc(String userName, String userPassword) {

        Log.d("variables", userName + ">>>" + userPassword);
        fbaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveinfo();
                            getLoginUser(task.getResult().getUser().getUid());
                        } else {
                            Toast.makeText(getApplicationContext(), "Lütfen ilgili alanları doğru doldurunuz", Toast.LENGTH_LONG).show();
                        }
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


    public void getLoginUser(String id) {
        DocumentReference docRef = databaseFirestore.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> userDoc) {
                if (userDoc.isSuccessful()) {
                    DocumentSnapshot document = userDoc.getResult();
                    if (document.exists()) {
                        loggedUser = document.toObject(User.class);
                        Intent i = new Intent(MainActivity.this, CommandActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Log.d("asd", "No such document");
                    }
                } else {
                    Log.d("asd", "get failed with ", userDoc.getException());
                }
            }
        });

    }

    ;
}



