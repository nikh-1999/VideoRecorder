package com.example.videorecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.videorecorder.Adapters.UserAdapter;
import com.example.videorecorder.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


///******* NO USE HERE ********\\\\

public class UsersList extends AppCompatActivity {

    ArrayList<Users> usersArrayList;
    ListView listView;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        usersArrayList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        userAdapter = new UserAdapter(this,usersArrayList);
        listView.setAdapter(userAdapter);
        queryListOfUser();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("userId",usersArrayList.get(i).getUserId().toString());
                startActivity(intent);
            }
        });
    }

    public void queryListOfUser(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    Users users = s.getValue(Users.class);
                    Log.i("Full Name",users.getFullName());
                    if(!users.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        usersArrayList.add(users);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}