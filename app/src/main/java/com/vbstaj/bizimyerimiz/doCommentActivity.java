package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.util.Date;

public class doCommentActivity extends BaseActivity {

    private Button commentButton;
    private EditText commentTitle;
    private EditText commentContent;

    @Override
    public int getContentView() {
        return R.layout.activity_do_comment;
    }


    @Override
    public void initView() {

        loggedUser =  getUser(fbaseAuth.getCurrentUser().getUid());
        commentButton = (Button)findViewById(R.id.commentButton);
        commentTitle = (EditText)findViewById(R.id.commentTitle);
        commentContent = (EditText)findViewById(R.id.commentContent);

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!commentTitle.getText().toString().equals("") && !commentContent.getText().toString().equals("")){
                    Date currentDate = new Date();
                    String contentToPush = commentContent.getText().toString().replaceAll("\\s{2,}", " ").replaceAll("[\\n\\r]"," ");
                    Comment comment = new Comment(loggedUser.getName(),loggedUser.getSurname(), commentTitle.getText().toString(), contentToPush, fbaseAuth.getUid(), currentDate);
                    databaseFirestore.collection("comments")
                            .add(comment)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("SUCCESS", "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("FAIL", "Error adding document", e);
                                }
                            });
                    Intent x = new Intent(doCommentActivity.this, CommandActivity.class);
                    startActivity(x);
                    finish();
                }else{
                    showMessage("Lütfen tüm alanları doldurunuz");
                }




            }
        });
    }

}
