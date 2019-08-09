package com.vbstaj.bizimyerimiz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;

import com.vbstaj.bizimyerimiz.model.Comment;
import com.vbstaj.bizimyerimiz.utils.Utils;

import java.util.Date;

public class NewCommentActivity extends BaseActivity {

    private Button commentButton;
    private EditText commentTitle;
    private EditText commentContent;
    private RatingBar ratingBar;
    private Button closePopupBtn;

    PopupWindow popupWindow;
    LinearLayout linearLayout1;

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

        linearLayout1 = findViewById(R.id.ll_mask);

        commentButton.setOnClickListener(v -> {
            linearLayout1.setVisibility(View.VISIBLE);
            String titleToPush = textCleaner(commentTitle.getText().toString());
            String contentToPush = textCleaner(commentContent.getText().toString());

            if(!titleToPush.equals("") && !contentToPush.equals("")){

                LayoutInflater layoutInflater = (LayoutInflater) NewCommentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.popup_rating,null);

                ratingBar = customView.findViewById(R.id.newCommentRating);
                closePopupBtn = (Button) customView.findViewById(R.id.skipButton);

                popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

                ratingBar.setOnRatingBarChangeListener((ratingBar, v1, b) -> {
                    if(ratingBar.getRating() == 0){
                        closePopupBtn.setText("atla");
                    }else{
                        closePopupBtn.setText("ONAYLA");
                    }
                });

                closePopupBtn.setOnClickListener(v12 -> {
                    Date currentDate = new Date();
                    Comment comment = new Comment(Utils.loggedUser.getName(),Utils.loggedUser.getSurname(), titleToPush, contentToPush, Utils.fbaseAuth.getCurrentUser().getUid(), currentDate,ratingBar.getRating());
                    Utils.databaseFirestore.collection("comments")
                            .add(comment)
                            .addOnSuccessListener(documentReference -> Log.d("SUCCESS", "DocumentSnapshot written with ID: " + documentReference.getId()))
                            .addOnFailureListener(e -> Log.w("FAIL", "Error adding document", e));
                    Intent x = new Intent(NewCommentActivity.this, CommentActivity.class);
                    startActivity(x);
                    finish();
                });

            }else{
                showMessage("Lütfen tüm alanları doldurunuz");
            }

        });
    }

}
