package com.example.holler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class Feed extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fb;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        fb = findViewById(R.id.floatingActionButton);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Currentuserid = user.getUid();
        recyclerView =findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Feed.this));
        databaseReference =database.getReference("All Questions");

        reference = db.collection("user").document(Currentuserid);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Create_feed.class));
            }
        });
        FirebaseRecyclerOptions<QuestionModel> options =
                new FirebaseRecyclerOptions.Builder<QuestionModel>()
                .setQuery(databaseReference,QuestionModel.class)
                .build();

        FirebaseRecyclerAdapter<QuestionModel, Viewholder_Question> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<QuestionModel, Viewholder_Question>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull Viewholder_Question holder, int position, @NonNull @NotNull QuestionModel model) {

                        holder.subitem(Feed.this, model.getEmail(), model.getTime(), model.getQuestion(),model.getPhone());


                    }

                    @NonNull
                    @NotNull
                    @Override
                    public Viewholder_Question onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.question_item, parent , false);

                        return  new Viewholder_Question(view);

                    }
                };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
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