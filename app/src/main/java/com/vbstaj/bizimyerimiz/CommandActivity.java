package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.CommentAdapter;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.util.ArrayList;


public class CommandActivity extends BaseActivity {

    private Button button;
    final ArrayList<Comment> allComments = new ArrayList<Comment>();
    ListView list;

    @Override
    public int getContentView() {
        return R.layout.activity_command;

    }

    @Override
    public void initView() {

        button = (Button)findViewById(R.id.logoutButton);
        final CommentAdapter adapter = new CommentAdapter(this, allComments);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                fbaseAuth.signOut();
                Intent i = new Intent(CommandActivity.this, MainActivity.class);
                startActivity(i);
                finish();*/

                adapter.notifyDataSetChanged();
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(allComments.get(i).getUserID());
            }
        });

        databaseFirestore.collection("comments").orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Comment tmp_comment = document.toObject(Comment.class);
                                allComments.add(tmp_comment);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Log.d("error", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}
