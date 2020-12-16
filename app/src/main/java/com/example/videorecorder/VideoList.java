package com.example.videorecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.videorecorder.Model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/// This Class Can be used for getting list of videos uploaded by particular user ///
// This is not implimented as of now

public class VideoList extends AppCompatActivity {
    public ArrayList<Post> arrayList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        queryListOfVideos();
    }


    public void queryListOfVideos(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Post");
        Log.i("Inside","Inside Function");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot s : snapshot.getChildren()){
                    Post post = s.getValue(Post.class);
                    Log.i("USERID",post.getUserId());
                    Log.i("VIDEO",post.getVideo());
                    if(post.getUserId() == FirebaseAuth.getInstance().getCurrentUser().getUid()){
                        Log.i("Video URL",post.getVideo());
                        arrayList.add(post);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.i("Error","ERRORORORORORORORORO");
            }
        });
    }
}