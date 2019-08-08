package com.vbstaj.bizimyerimiz.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vbstaj.bizimyerimiz.R;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHoder>{

    private List<Comment> list;
    private Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

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
    public void onBindViewHolder(final MyHoder holder, final int position) {
        Comment mylist = list.get(position);
        holder.name.setText(mylist.getName());
        if(mylist.getRating() != 0){
            holder.rate.setVisibility(View.VISIBLE);
            holder.rate.setRating(mylist.getRating());
        }
        holder.content.setText(mylist.getContent().length() > 60 ? mylist.getContent().substring(0,60) + "..." : mylist.getContent());

        holder.title.setText(mylist.getTitle());

        Date tmpDate = mylist.getCreatedAt();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd.MM.YYYY");
        holder.date.setText(simpleDateFormat.format(tmpDate));


        holder.itemView.setOnClickListener(view -> {
            holder.content.setText(list.get(position).getContent());
            listener.ItemClick(mylist,position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHoder extends RecyclerView.ViewHolder{
        TextView name,title,content,date;
        RatingBar rate;


        public MyHoder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.userNameContent);
            title= (TextView) itemView.findViewById(R.id.titleContent);
            content= (TextView) itemView.findViewById(R.id.textContent);
            date= (TextView) itemView.findViewById(R.id.dateContent);
            rate = (RatingBar) itemView.findViewById(R.id.commentRating);

        }
    }

    public interface OnItemClickListener {
        void ItemClick(Comment comment, int pos);
    }

}