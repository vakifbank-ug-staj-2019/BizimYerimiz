package com.vbstaj.bizimyerimiz.listAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.vbstaj.bizimyerimiz.R;
import com.vbstaj.bizimyerimiz.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHoder> {

    private List<User> list;
    private Context context;
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UserAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_list_view_item, parent, false);
        MyHoder myHoder = new MyHoder(view);


        return myHoder;
    }

    @Override
    public void onBindViewHolder(final MyHoder holder, final int position) {
        User mylist = list.get(position);
        holder.name.setText(mylist.getName() + " " + mylist.getSurname());

        if (holder.sectionsToHide.getVisibility() == View.VISIBLE)
            holder.sectionsToHide.setVisibility(View.GONE);

        Date fullDate = mylist.getBirthdate();
        SimpleDateFormat onlyDate = new SimpleDateFormat("dd/MM/YYYY");
        holder.birthdate.setText(onlyDate.format(fullDate));

        holder.age.setText("(" + getAge(fullDate) + ")");

        holder.city.setText(mylist.getCity());

        holder.registerdate.setText(onlyDate.format(mylist.getRegisteredAt()));

        holder.itemView.setOnClickListener(view -> {
            if (holder.sectionsToHide.getVisibility() == View.VISIBLE)
                holder.sectionsToHide.setVisibility(View.GONE);
            else
                holder.sectionsToHide.setVisibility(View.VISIBLE);
            listener.ItemClick(mylist, position);
        });

        holder.mailButton.setOnClickListener(view -> {
            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("message/rfc822");
            mailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{mylist.getEmail()});
            try {
                context.startActivity(Intent.createChooser(mailIntent,mylist.getName() + " " + mylist.getSurname() + " kişisine mail yolla"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(view.getContext(), "Hiçbir mail uygulaması yüklü değil", Toast.LENGTH_SHORT).show();
            }
        });

        holder.phoneCallButton.setOnClickListener(view -> {
            Intent phonecallIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mylist.getPhoneNumber(), null));
            context.startActivity(phonecallIntent);
        });

        holder.linkedinButton.setOnClickListener(view -> {
            Intent linkedinIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://almondmendoza.com/android-applications/"));
            linkedinIntent.setData(Uri.parse("https://www.linkedin.com/in/" + mylist.getLinkedinUsername()));
            context.startActivity(linkedinIntent);
        });

        if(mylist.isAdmin()){
            Drawable image = context.getResources().getDrawable( R.drawable.ic_admin_32 );
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds( 0, 0, w, h );
            holder.name.setCompoundDrawables( null, null, image, null );
        }
        if(!mylist.isAdmin()){
            holder.name.setCompoundDrawables( null, null, null, null );
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public long getAge(Date birthdate) {
        Date currentDate = new Date();
        long difference = Math.abs(currentDate.getTime() - birthdate.getTime());
        return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS) / 365;
    }


    class MyHoder extends RecyclerView.ViewHolder {
        ConstraintLayout sectionsToHide;
        TextView name, birthdate, age, city,registerdate;
        Button linkedinButton,phoneCallButton,mailButton;


        public MyHoder(View itemView) {
            super(itemView);

            sectionsToHide = (ConstraintLayout) itemView.findViewById(R.id.allSections);
            name = (TextView) itemView.findViewById(R.id.nameSurname);
            birthdate = (TextView) itemView.findViewById(R.id.birthDate);
            age = (TextView) itemView.findViewById(R.id.birthdayAge);
            city = (TextView) itemView.findViewById(R.id.locationContent);
            registerdate = (TextView) itemView.findViewById(R.id.registerDateContent);

            linkedinButton = (Button)itemView.findViewById(R.id.linkedinButton);
            phoneCallButton = (Button)itemView.findViewById(R.id.phoneCallButton);
            mailButton = (Button)itemView.findViewById(R.id.mailButton);


        }
    }

    public interface OnItemClickListener {
        void ItemClick(User user, int pos);
    }

}