package com.vbstaj.bizimyerimiz;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vbstaj.bizimyerimiz.model.User;

public abstract class BaseActivity extends AppCompatActivity {

    public FirebaseFirestore databaseFirestore;
    public FirebaseAuth fbaseAuth;
    public FirebaseUser fbaseUser;
    public User loggedUser;


    @LayoutRes
    public abstract int getContentView();

    public abstract void initView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        databaseFirestore = FirebaseFirestore.getInstance();
        fbaseAuth = FirebaseAuth.getInstance();
        fbaseUser = fbaseAuth.getCurrentUser();
        loggedUser = new User();

        initView();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
