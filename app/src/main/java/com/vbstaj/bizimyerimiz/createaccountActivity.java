package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vbstaj.bizimyerimiz.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class createaccountActivity extends BaseActivity {

    private EditText regName,regSurname,regEmail,regPassword,regRePassword,regBDate,regCity,regPhone,regLinkedin;
    private Button createaccount;

    @Override
    public int getContentView() {
        return R.layout.activity_createaccount;
    }


    @Override
    public void initView() {


        fbaseAuth = FirebaseAuth.getInstance();

        regName = (EditText)findViewById(R.id.userName);
        regSurname = (EditText)findViewById(R.id.surName);
        regEmail = (EditText)findViewById(R.id.eMail);
        regPassword = (EditText)findViewById(R.id.password);
        regRePassword = (EditText)findViewById(R.id.repassword);
        createaccount = (Button)findViewById(R.id.createaccount);
        regBDate = (EditText) findViewById(R.id.date);
        regCity = (EditText) findViewById(R.id.country);
        regPhone = (EditText) findViewById(R.id.phoneNumber);
        regLinkedin = (EditText) findViewById(R.id.linkedIn);


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
                }else if(!regBDate.getText().toString().matches("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$")){
                    showMessage("Doğum tarihi formatı GG/AA/YYYY şeklinde olmalıdır");
                }else if(regPassword.getText().toString().equals("") || regRePassword.getText().toString().equals("")){
                    showMessage("Şifre alanları boş bırakılamaz.");
                }else if(!regPassword.getText().toString().equals(regRePassword.getText().toString())){
                    showMessage("Şifreler aynı olmalıdır.");
                }else if(regBDate.getText().toString().equals("")){
                    showMessage("Doğum tarihi boş bırakılamaz");
                }else if(regCity.getText().toString().equals("")){
                    showMessage("Yaşadığınız il boş bırakılamaz");
                }else if(regPhone.getText().toString().equals("")){
                    showMessage("Telefon numaranızı giriniz");
                }else if(regLinkedin.getText().toString().equals("")){
                    showMessage("Linkedin kullanıcı adınızı giriniz.");
                }else{
                    fbaseAuth.createUserWithEmailAndPassword(regEmail.getText().toString(),regPassword.getText().toString())
                            .addOnCompleteListener(createaccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SimpleDateFormat onlyDate = new SimpleDateFormat("dd/MM/yy");
                                        Date currentDate = new Date();
                                        Date bDate = null;
                                        try {
                                            bDate = onlyDate.parse(regBDate.getText().toString());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        String registeredID = task.getResult().getUser().getUid();
                                        User registeredUser = new User(regName.getText().toString(), regSurname.getText().toString(), bDate, currentDate, regCity.getText().toString(), regEmail.getText().toString(), regLinkedin.getText().toString(), regPhone.getText().toString(),false);

                                        databaseFirestore.collection("users").document(registeredID)
                                                .set(registeredUser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        showMessage("Üyeliğiniz başarıyla oluşturulmuştur.");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        showMessage("Bir hata oluştu.");
                                                    }
                                                });
                                        startActivity(new Intent(createaccountActivity.this, MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        showMessage("Daha önceden bu eposta ile hesap oluşturulmuş");
                                    }

                                }
                            });
                }

            }
        });
    }
}
