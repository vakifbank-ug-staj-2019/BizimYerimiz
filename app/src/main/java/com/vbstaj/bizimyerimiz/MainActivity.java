package com.vbstaj.bizimyerimiz;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
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


    private FirebaseAuth fbaseAuth;
    private FirebaseUser fbaseUser;

    @Override
    public int getContentView() {
        return R.layout.activity_main;

    }

    @Override
    public void initView() {

        emailField = (EditText)findViewById(R.id.emailInput);
        passwordField = (EditText)findViewById(R.id.passwordInput);
        loginButton = (Button)findViewById(R.id.loginButton);
        register = (Button) findViewById(R.id.registerButton);


        fbaseAuth = FirebaseAuth.getInstance();
        fbaseUser = fbaseAuth.getCurrentUser();


        if(fbaseUser != null){ // check user session
            Intent i = new Intent(MainActivity.this, CommandActivity.class);
            startActivity(i);
            finish();
        }
       /**Giriş Yap butonunu çalıştırır.*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailField.getText().toString();
                String userPassword = passwordField.getText().toString();
                if (userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                } else {
                    loginFunc(userEmail,userPassword);
                }
            }
        });


        /**Kayıt ol Butonunu çalıştırır.*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent u = new Intent(MainActivity.this,createaccountActivity.class);
               startActivity(u);
            }
        });

    }

    public void loginFunc(String userName,String userPassword) {

        Log.d("variables", userName + ">>>" + userPassword);
        fbaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            DocumentReference docRef = databaseFirestore.collection("users").document(task.getResult().getUser().getUid());
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> userDoc) {
                                    if (userDoc.isSuccessful()) {
                                        DocumentSnapshot document = userDoc.getResult();
                                        if (document.exists()) {
                                            loggedUser = document.toObject(User.class);
                                            String message=("Hosgeldin "+loggedUser.getName());
                                            showMessage(message);
                                        } else {
                                            Log.d("asd", "No such document");
                                        }
                                    } else {
                                        Log.d("asd", "get failed with ", userDoc.getException());
                                    }
                                }
                            });


                           Intent i = new Intent(MainActivity.this,CommandActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
}
