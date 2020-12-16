package com.example.videorecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.videorecorder.Model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    String receiverId;
    String message;
    ArrayList<String> messages;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText messageView;

    public void sendMessageButton(View view){
        sendMessage();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        listView = findViewById(R.id.listView);
        Intent intent = getIntent();
        messages = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,messages);
        listView.setAdapter(arrayAdapter);
        messageView = findViewById(R.id.message);
        receiverId = intent.getStringExtra("userId");
        queryMessages();
    }
    public void queryMessages(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot s : snapshot.getChildren()){
                    Message m = s.getValue(Message.class);
                    assert m != null;
                    if(m.getReceiver().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        messages.add(m.getMessage());
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //Sender's id receiver's id message
    public void sendMessage(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        message = messageView.getText().toString();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("receiver",receiverId);
        hashMap.put("message",message);
        messages.add(message);
        arrayAdapter.notifyDataSetChanged();
        databaseReference.push().setValue(hashMap);
        message = "";
        messageView.setText("");
    }
}