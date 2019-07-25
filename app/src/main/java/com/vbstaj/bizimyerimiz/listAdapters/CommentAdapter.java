package com.vbstaj.bizimyerimiz.listAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vbstaj.bizimyerimiz.R;
import com.vbstaj.bizimyerimiz.model.Comment;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private final Activity context;
    private final ArrayList<Comment> allComments;

    public CommentAdapter(Activity context, ArrayList<Comment> comment) {
        super(context, R.layout.list_view_item, comment);

        this.context=context;
        this.allComments = comment;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_view_item, null,true);

        TextView commentContent = (TextView) rowView.findViewById(R.id.textContent);
        TextView commentUser = (TextView) rowView.findViewById(R.id.userNameContent);
        TextView commentDate = (TextView) rowView.findViewById(R.id.dateContent);

        commentContent.setText(allComments.get(position).getContent());
        commentUser.setText(allComments.get(position).getName());
        commentDate.setText(allComments.get(position).getCreatedAt().toString());

        return rowView;

    };
}