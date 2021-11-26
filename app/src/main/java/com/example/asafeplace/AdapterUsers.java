package com.example.asafeplace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {
    Context context;
    List<User> userList;

    //constructor
    public AdapterUsers(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //inflating layout (row_user.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        String userFullName = userList.get(i).getFullName();
        String userEmail = userList.get(i).getEmail();

        //set data
        myHolder.fullName.setText(userFullName);
        myHolder.email.setText(userEmail);

        //handle item clicked
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+userEmail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view Holder class
    class MyHolder extends RecyclerView.ViewHolder{
        TextView fullName, email;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //initialise values
           fullName = itemView.findViewById(R.id.fullName);
           email= itemView.findViewById(R.id.email);
        }
    }
}
