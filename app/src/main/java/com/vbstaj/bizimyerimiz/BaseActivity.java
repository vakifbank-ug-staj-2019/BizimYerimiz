package com.vbstaj.bizimyerimiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vbstaj.bizimyerimiz.model.User;
import com.vbstaj.bizimyerimiz.utils.Utils;

public abstract class BaseActivity extends AppCompatActivity {
    @LayoutRes
    public abstract int getContentView();

    public abstract void initView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        Utils.databaseFirestore = FirebaseFirestore.getInstance();
        Utils.fbaseAuth = FirebaseAuth.getInstance();

        initView();
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void getLoginUser(String id) {
        DocumentReference docRef = Utils.databaseFirestore.collection("users").document(id);
        docRef.get().addOnCompleteListener(userDoc -> {
            if (userDoc.isSuccessful()) {
                DocumentSnapshot document = userDoc.getResult();
                if (document.exists()) {
                    Utils.loggedUser = document.toObject(User.class);
                } else {
                    Log.d("asd", "No such document");
                }
            } else {
                Log.d("asd", "get failed with ", userDoc.getException());
            }
        });
    }

    public String textCleaner(String text){
        return text.trim().replaceAll("\\s{2,}", " ").replaceAll("[\\n\\r]"," ");
    }
}
