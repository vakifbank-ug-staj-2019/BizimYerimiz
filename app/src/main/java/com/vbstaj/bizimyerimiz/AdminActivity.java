package com.vbstaj.bizimyerimiz;

import android.util.Log;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vbstaj.bizimyerimiz.listAdapters.UserAdapter;
import com.vbstaj.bizimyerimiz.model.User;
import com.vbstaj.bizimyerimiz.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminActivity extends BaseActivity {

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

        recycle = findViewById(R.id.listViewAdmin);
        list = new ArrayList<>();
        recyclerAdapter = new UserAdapter(list,this);
        RecyclerView.LayoutManager recyce = new LinearLayoutManager(this);
        recycle.setLayoutManager(recyce);
        recycle.setItemAnimator( new DefaultItemAnimator());
        recycle.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener((user, pos) -> {
            if(lastPos != -1 && lastPos != pos){
                recyclerAdapter.notifyItemChanged(lastPos);
            }
            lastPos = pos;
        });

        Utils.databaseFirestore.collection("users")
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
                            User tmp_comment = doc.toObject(User.class);
                            list.add(tmp_comment);
                        }
                    }
                    Collections.sort(list);
                    recycle.setAdapter(recyclerAdapter);
                });
    }

}
