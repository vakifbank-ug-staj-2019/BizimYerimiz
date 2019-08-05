package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.vbstaj.bizimyerimiz.model.Comment;
import com.vbstaj.bizimyerimiz.utils.Utils;

import java.util.Date;

public class NewCommentActivity extends BaseActivity {

    private Button commentButton;
    private EditText commentTitle;
    private EditText commentContent;

    @Override
    public int getContentView() {
        return R.layout.activity_new_comment;
    }

    @Override
    public void onBackPressed()
    {
        Intent x = new Intent(NewCommentActivity.this, CommentActivity.class);
        startActivity(x);
        finish();
    }

    @Override
    public void initView() {
        commentButton = findViewById(R.id.commentButton);
        commentTitle = findViewById(R.id.commentTitle);
        commentContent = findViewById(R.id.commentContent);

        commentButton.setOnClickListener(view -> {
            if(!commentTitle.getText().toString().equals("") && !commentContent.getText().toString().equals("")){
                Date currentDate = new Date();
                String contentToPush = commentContent.getText().toString().replaceAll("\\s{2,}", " ").replaceAll("[\\n\\r]"," ");
                Comment comment = new Comment(Utils.loggedUser.getName(),Utils.loggedUser.getSurname(), commentTitle.getText().toString(), contentToPush, Utils.fbaseAuth.getCurrentUser().getUid(), currentDate);
                Utils.databaseFirestore.collection("comments")
                        .add(comment)
                        .addOnSuccessListener(documentReference -> Log.d("SUCCESS", "DocumentSnapshot written with ID: " + documentReference.getId()))
                        .addOnFailureListener(e -> Log.w("FAIL", "Error adding document", e));
                Intent x = new Intent(NewCommentActivity.this, CommentActivity.class);
                startActivity(x);
                finish();
            }else{
                showMessage("Lütfen tüm alanları doldurunuz");
            }




        });
    }

}
