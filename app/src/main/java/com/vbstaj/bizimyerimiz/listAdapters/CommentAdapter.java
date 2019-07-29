package com.vbstaj.bizimyerimiz.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vbstaj.bizimyerimiz.R;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHoder>{

    List<Comment> list;
    Context context;

    public CommentAdapter(List<Comment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        Comment mylist = list.get(position);
        holder.name.setText(mylist.getName());
        holder.content.setText(mylist.getContent());
        holder.title.setText(mylist.getTitle());

        Date tmpDate = mylist.getCreatedAt();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM.YYYY");
        holder.date.setText(simpleDateFormat.format(tmpDate));
    }

    @Override
    public int getItemCount() {

        int arr = 0;

        try{
            if(list.size()==0){

                arr = 0;

            }
            else{

                arr=list.size();
            }



        }catch (Exception e){



        }

        return arr;

    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView name,title,content,date;


        public MyHoder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.userNameContent);
            title= (TextView) itemView.findViewById(R.id.titleContent);
            content= (TextView) itemView.findViewById(R.id.textContent);
            date= (TextView) itemView.findViewById(R.id.dateContent);

        }
    }

}