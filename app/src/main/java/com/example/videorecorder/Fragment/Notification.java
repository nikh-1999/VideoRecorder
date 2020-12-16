package com.example.videorecorder.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.videorecorder.Model.Message;
import com.example.videorecorder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends Fragment {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> messages;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification,container,false);
        listView = view.findViewById(R.id.listView);
        messages = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,messages);
        listView.setAdapter(arrayAdapter);
        queryMessages();
        return view;
    }

    public void queryMessages(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                Log.i("HERE","HERESDFDFSDF");
                for(DataSnapshot s : snapshot.getChildren()){
                    Message m = s.getValue(Message.class);
                    Log.i("Message",m.getMessage());
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
}