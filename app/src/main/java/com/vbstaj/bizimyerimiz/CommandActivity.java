package com.vbstaj.bizimyerimiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.CommentAdapter;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.util.ArrayList;


public class CommandActivity extends BaseActivity {

    private Button refresh;
    private ImageButton out;
    private Button commentbutton;

    final ArrayList<Comment> allComments = new ArrayList<Comment>();
    ListView list;

    @Override
    public int getContentView() {
        return R.layout.activity_command;
    }


    @Override
    public void initView() {

        refresh = (Button)findViewById(R.id.refreshButton);
        out= (ImageButton) findViewById (R.id.hB);
        commentbutton=(Button)findViewById(R.id.commandButton);
        final CommentAdapter adapter = new CommentAdapter(this, allComments);
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);

       /** refresh.setOnClickListener(new View.OnClickListener() {
       //Burada yenile butonunun özellikleri verilecek.
       @Override
           public void onClick(View view) {


            }
        });*/

       out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbaseAuth.signOut();
                Intent i = new Intent(CommandActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                //adapter.notifyDataSetChanged();
            }
        });

        commentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent y = new Intent(CommandActivity.this, doCommentActivity.class);
                startActivity(y);

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
