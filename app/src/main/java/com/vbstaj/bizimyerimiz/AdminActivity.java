package com.vbstaj.bizimyerimiz;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.UserAdapter;
import com.vbstaj.bizimyerimiz.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends BaseActivity {

    private Button geri;
    private UserAdapter recyclerAdapter;
    private int lastPos = -1;

    List<User> list;
    RecyclerView recycle;

    @Override
    public int getContentView() {
        return R.layout.activity_admin;
    }

    @Override
    public void initView() {

        recycle = (RecyclerView) findViewById(R.id.listViewAdmin);
        list = new ArrayList<User>();

        recyclerAdapter = new UserAdapter(list,this);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(this);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener((user, pos) -> {
            if(lastPos != -1 && lastPos != pos){
                recyclerAdapter.notifyItemChanged(pos);
            }

            lastPos = pos;
        });

        databaseFirestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User tmp_user = document.toObject(User.class);
                            list.add(tmp_user);
                        }
                        recycle.setAdapter(recyclerAdapter);
                    } else {
                        Log.d("error", "Error getting documents: ", task.getException());
                    }
                });











    }

}
