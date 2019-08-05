package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.vbstaj.bizimyerimiz.model.User;
import com.vbstaj.bizimyerimiz.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends BaseActivity {

    private EditText regName,regSurname,regEmail,regPassword,regRePassword,regBDate,regCity,regPhone,regLinkedin;
    private Button createaccount;

    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }


    @Override
    public void initView() {

        regName = findViewById(R.id.userName);
        regSurname = findViewById(R.id.surName);
        regEmail = findViewById(R.id.eMail);
        regPassword = findViewById(R.id.password);
        regRePassword = findViewById(R.id.repassword);
        createaccount = findViewById(R.id.createaccount);
        regBDate = findViewById(R.id.date);
        regCity = findViewById(R.id.country);
        regPhone = findViewById(R.id.phoneNumber);
        regLinkedin = findViewById(R.id.linkedIn);

        createaccount.setOnClickListener(view -> {
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
            }else if(!regPhone.getText().toString().matches("^05[0-9]{9}$")){
                showMessage("Telefon 05XXXXXXXXX formatında olmalıdır");
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
                Utils.fbaseAuth.createUserWithEmailAndPassword(regEmail.getText().toString(),regPassword.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, task -> {
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

                                Utils.databaseFirestore.collection("users").document(registeredID)
                                        .set(registeredUser)
                                        .addOnSuccessListener(aVoid -> showMessage("Üyeliğiniz başarıyla oluşturulmuştur."))
                                        .addOnFailureListener(e -> showMessage("Bir hata oluştu."));
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                showMessage("Daha önceden bu eposta ile hesap oluşturulmuş");
                            }

                        });
            }
        });
    }
}
