package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.CommentAdapter;
import com.vbstaj.bizimyerimiz.model.Comment;
import com.vbstaj.bizimyerimiz.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class CommandActivity extends BaseActivity {

    private Button refresh;
    private Button out;
    private Button commentbutton;
    private Button userListButton;
    private CommentAdapter recyclerAdapter;
    private int lastPos = -1;
    private static final int TIME_INTERVAL = 2000; // 2basma arasındaki saniye en fazla 2 olablir.
    private long mBackPressed;
    List<Comment> list;
    RecyclerView recycle;

    @Override
    public int getContentView() {
        return R.layout.activity_command;
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


        userListButton = (Button)findViewById(R.id.userListButton);
        refresh = (Button)findViewById(R.id.refreshButton);
        out= (Button) findViewById (R.id.out);
        commentbutton=(Button)findViewById(R.id.commandButton);

        recycle = (RecyclerView) findViewById(R.id.listView);
        list = new ArrayList<Comment>();

        recyclerAdapter = new CommentAdapter(list,this);
        //RecyclerView.LayoutManager recyce = new GridLayoutManager(this,1);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(this);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

        if(Utils.loggedUser.isAdmin()){
            commentbutton.setVisibility(View.GONE);
            userListButton.setVisibility(View.VISIBLE);
        }else{
            commentbutton.setVisibility(View.VISIBLE);
            userListButton.setVisibility(View.GONE);
        }

        userListButton.setOnClickListener(view -> {
            startActivity(new Intent(CommandActivity.this, AdminActivity.class));

        });

       refresh.setOnClickListener(view -> {
           list.clear();
           databaseFirestore.collection("comments").orderBy("createdAt", Query.Direction.DESCENDING)
                   .get()
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Comment tmp_comment = document.toObject(Comment.class);
                               list.add(tmp_comment);
                           }
                           recycle.setAdapter(recyclerAdapter);
                       } else {
                           Log.d("error", "Error getting documents: ", task.getException());
                       }
                   });
       });
       recyclerAdapter.setOnItemClickListener((comment, pos) -> {
           if(lastPos != -1 && lastPos != pos){
               recyclerAdapter.notifyItemChanged(lastPos);
           }

           lastPos = pos;
       });

       out.setOnClickListener(view -> {
           fbaseAuth.signOut();
           Utils.loggedUser = null;
           Intent i = new Intent(CommandActivity.this, MainActivity.class);
           startActivity(i);
           finish();
       });

        commentbutton.setOnClickListener(view -> {

            Intent y = new Intent(CommandActivity.this, doCommentActivity.class);
            startActivity(y);

        });

        databaseFirestore.collection("comments").orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Comment tmp_comment = document.toObject(Comment.class);
                            list.add(tmp_comment);
                        }
                        recycle.setAdapter(recyclerAdapter);
                    } else {
                        Log.d("error", "Error getting documents: ", task.getException());
                    }
                });

    }
}
