package com.vbstaj.bizimyerimiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public User getUser(String id){
        DocumentReference docRef = databaseFirestore.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> userDoc) {
                if (userDoc.isSuccessful()) {
                    DocumentSnapshot document = userDoc.getResult();
                    if (document.exists()) {
                        loggedUser = document.toObject(User.class);
                    } else {
                        Log.d("asd", "No such document");
                    }
                } else {
                    Log.d("asd", "get failed with ", userDoc.getException());
                }
            }
        });
        return  loggedUser;
    };

}
