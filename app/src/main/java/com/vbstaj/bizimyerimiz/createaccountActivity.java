package com.vbstaj.bizimyerimiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.CommentAdapter;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.util.ArrayList;

public class createaccountActivity extends BaseActivity {

    private EditText regName,regSurname,regEmail,regPassword,regRePassword;
    private Button createaccount;

    @Override
    public int getContentView() {
        return R.layout.activity_createaccount;
    }


    @Override
    public void initView() {

        regName = (EditText)findViewById(R.id.userName);
        regSurname = (EditText)findViewById(R.id.surName);
        regEmail = (EditText)findViewById(R.id.eMail);
        regPassword = (EditText)findViewById(R.id.password);
        regRePassword = (EditText)findViewById(R.id.repassword);
        createaccount = (Button)findViewById(R.id.createaccount);


        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regName.getText().toString().equals("")){
                    showMessage("Ad boş bırakılamaz.");
                }else if(regSurname.getText().toString().equals("")){
                    showMessage("Soyad boş bırakılamaz.");
                }else if(regEmail.getText().toString().equals("")){
                    showMessage("Email boş bırakılamaz.");
                }else if(!regEmail.getText().toString().matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")){
                    showMessage("Lütfen geçerli e-posta giriniz.");
                }else if(regPassword.getText().toString().equals("") || regRePassword.getText().toString().equals("")){
                    showMessage("Şifre alanları boş bırakılamaz.");
                }else if(!regPassword.getText().toString().equals(regRePassword.getText().toString())){
                    showMessage("Şifreler aynı olmalıdır.");
                }

            }
        });
    }
}
