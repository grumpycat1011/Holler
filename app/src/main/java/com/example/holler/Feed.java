package com.example.holler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Feed extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        fb = findViewById(R.id.floatingActionButton);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Currentuserid = user.getUid();

        reference = db.collection("user").document(Currentuserid);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Create_feed.class));
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onStart() {
        super.onStart();
        reference.get()
                .addOnCompleteListener(task -> {
                    if(task.getResult().exists()){
                        String url = task.getResult().getString("url");
                    }
                    else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}