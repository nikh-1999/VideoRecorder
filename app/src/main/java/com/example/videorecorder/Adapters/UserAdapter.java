package com.example.videorecorder.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.videorecorder.Model.Users;
import com.example.videorecorder.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<Users> {

    private Context context;
    private List<Users> users;

    public UserAdapter(Context context,List<Users> users){
        super(context, R.layout.users_list,users);
        this.context = context;
        this.users = users;
    }

    public static class ViewHolder{
        TextView fullName;
        TextView eMail;

        public ViewHolder(TextView fullName, TextView eMail) {
            this.fullName = fullName;
            this.eMail = eMail;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.users_list,parent,false);
            TextView eMail = view.findViewById(R.id.eMail);
            TextView fullName = view.findViewById(R.id.fullName);

            ViewHolder viewHolder = new ViewHolder(fullName,eMail);
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.fullName.setText(users.get(position).getFullName());
        viewHolder.eMail.setText(users.get(position).geteMail());

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}