package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vbstaj.bizimyerimiz.listAdapters.CommentAdapter;
import com.vbstaj.bizimyerimiz.model.Comment;
import com.vbstaj.bizimyerimiz.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CommentActivity extends BaseActivity {

    private Button out;
    private Button commentbutton;
    private Button userListButton;
    private CommentAdapter recyclerAdapter;
    private int lastPos = -1;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    List<Comment> list;
    RecyclerView recycle;

    @Override
    public int getContentView() {
        return R.layout.activity_comment;
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Uygulamadan çıkmak için bir daha basın", Toast.LENGTH_SHORT).show(); }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public void initView() {


        userListButton = findViewById(R.id.userListButton);
        out= findViewById (R.id.out);
        commentbutton= findViewById(R.id.commandButton);

        recycle = findViewById(R.id.listView);
        list = new ArrayList<>();
        recyclerAdapter = new CommentAdapter(list,this);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(this);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

        FirebaseMessaging.getInstance().subscribeToTopic("NewComment");

        if(Utils.loggedUser.isAdmin()){
            commentbutton.setVisibility(View.GONE);
            userListButton.setVisibility(View.VISIBLE);
        }else{
            commentbutton.setVisibility(View.VISIBLE);
            userListButton.setVisibility(View.GONE);
        }

        userListButton.setOnClickListener(view -> {
            startActivity(new Intent(CommentActivity.this, AdminActivity.class));

        });

       recyclerAdapter.setOnItemClickListener((comment, pos) -> {
           if(lastPos != -1 && lastPos != pos){
               recyclerAdapter.notifyItemChanged(lastPos);
           }
           lastPos = pos;
       });

       out.setOnClickListener(view -> {
           Utils.fbaseAuth.signOut();
           Utils.loggedUser = null;
           FirebaseMessaging.getInstance().unsubscribeFromTopic("NewComment");
           Intent i = new Intent(CommentActivity.this, MainActivity.class);
           startActivity(i);
           finish();
       });

        commentbutton.setOnClickListener(view -> {
            Intent y = new Intent(CommentActivity.this, NewCommentActivity.class);
            startActivity(y);
            finish();

        });

        Utils.databaseFirestore.collection("comments")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w("ERROR", "Listen failed.", e);
                        return;
                    }
                    if(!list.isEmpty()){
                        list.clear();
                    }
                    for (QueryDocumentSnapshot doc : value) {
                        if (doc != null) {
                            Comment tmp_comment = doc.toObject(Comment.class);
                            list.add(tmp_comment);
                        }
                    }
                    Collections.sort(list);
                    recycle.setAdapter(recyclerAdapter);
                });
    }
}
