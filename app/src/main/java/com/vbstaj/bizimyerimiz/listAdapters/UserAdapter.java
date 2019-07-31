package com.vbstaj.bizimyerimiz.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vbstaj.bizimyerimiz.R;
import com.vbstaj.bizimyerimiz.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHoder>{

    private List<User> list;
    private Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_list_view_item,parent,false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(final MyHoder holder, final int position) {
        User mylist = list.get(position);
        holder.name.setText(mylist.getName() + " " + mylist.getSurname());
        if(mylist.isGender() == true){
            holder.femaleIcon.setVisibility(View.GONE);
        }else{
            holder.maleIcon.setVisibility(View.GONE);
        }

        Date fullDate = mylist.getBirthdate();
        SimpleDateFormat onlyDate = new SimpleDateFormat("dd/MM/YYYY");
        holder.birthdate.setText(onlyDate.format(fullDate));

        // AGE
        holder.age.setText("(22)");

        holder.city.setText(mylist.getCity());

        holder.mail.setText(mylist.getEmail());

        holder.phone.setText(mylist.getPhoneNumber());

        holder.linkedin.setText(mylist.getLinkedinUsername());

        holder.registerdate.setText(onlyDate.format(mylist.getRegisteredAt()));

        holder.itemView.setOnClickListener(view -> {
            holder.sectionsToHide.setVisibility(View.VISIBLE);
            listener.ItemClick(list.get(position),position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHoder extends RecyclerView.ViewHolder{
        ConstraintLayout sectionsToHide;
        TextView name,birthdate,age,city,mail,phone,linkedin,registerdate;
        ImageView femaleIcon,maleIcon;


        public MyHoder(View itemView) {
            super(itemView);

            sectionsToHide = (ConstraintLayout) itemView.findViewById(R.id.allSections);

            maleIcon = (ImageView)itemView.findViewById(R.id.maleIcon);
            femaleIcon = (ImageView)itemView.findViewById(R.id.femaleIcon);

            name = (TextView)itemView.findViewById(R.id.nameSurname);
            birthdate = (TextView)itemView.findViewById(R.id.birthDate);
            age = (TextView)itemView.findViewById(R.id.birthdayAge);
            city = (TextView)itemView.findViewById(R.id.locationContent);
            mail = (TextView)itemView.findViewById(R.id.emailContent);
            phone = (TextView)itemView.findViewById(R.id.numberContent);
            linkedin = (TextView)itemView.findViewById(R.id.linkedinContent);
            registerdate = (TextView)itemView.findViewById(R.id.registerDateContent);


        }
    }

    public interface OnItemClickListener {
        void ItemClick(User user, int pos);
    }

}