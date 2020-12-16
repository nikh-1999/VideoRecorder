package com.example.videorecorder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.videorecorder.Model.Users;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// Main Activity of the app will launch the sign-in panel which is based on FirebaseUI library which provides
// many features for sign-in. We are currently using only Email Sign-in Method
// other sign in methods can be used such as Phone, G-Mail, Facebook

public class MainActivity extends AppCompatActivity {
    int SIGN_IN = 1;
    int LOG_IN = 2;

    public void addUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("fullName",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        hashMap.put("eMail",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        databaseReference.push().setValue(hashMap);
    }

    public void signIn(View view) {
       createSignInIntent();
    }

    public void logIn(View view){
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(this,BaseActivity.class);
            startActivity(intent);
            finish();
        } else {
            createLogInIntent();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to create the sign-in intent
    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), SIGN_IN);
    }

    public void createLogInIntent(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), LOG_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // if sign-in is successful
                addUser();
                Intent intent = new Intent(this,BaseActivity.class);
                startActivity(intent);
                finish();
            }
        }

        if(requestCode == LOG_IN){
            if(resultCode == RESULT_OK){
                Intent intent = new Intent(this,BaseActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}