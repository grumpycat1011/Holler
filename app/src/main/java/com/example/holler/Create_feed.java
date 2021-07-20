package com.example.holler;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class Create_feed extends AppCompatActivity {
    String question;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://holler-cfc3f-default-rtdb.firebaseio.com/");
    DatabaseReference Allquestions, UserQuestions;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    QuestionModel member;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feed);
        EditText phoneet = findViewById(R.id.phone_et);
        EditText emailet = findViewById(R.id.email_et);
        EditText edittext = findViewById(R.id.question_et);
        Button postbtn = findViewById(R.id.postbtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Currentuserid = user.getUid();
        documentReference = db.collection("user").document(Currentuserid);
        Allquestions = database.getReference("All Questions");
        UserQuestions = database.getReference("User Questions").child(Currentuserid);
        member = new QuestionModel();
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailet.getText().toString();
                String phone = phoneet.getText().toString();
                question = edittext.getText().toString();
                Calendar cdate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                final String savedate = currentdate.format(cdate.getTime());

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());

                String time = savedate + ":" + savetime;

                if(question != null){
                    member.setEmail(email);
                    member.setPhone(phone);
                    member.setQuestion(question);
                    member.setUserid(uid);
                    member.setTime(time);
                    String id = UserQuestions.push().getKey();
                    UserQuestions.child(id).setValue(member);

                    String child =Allquestions.push().getKey();
                    member.setKey(id);
                    Allquestions.child(child).setValue(member);
                }else{
                    Toast.makeText(Create_feed.this, "Please enter a question", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        documentReference.get()
                .addOnCompleteListener(task -> {
                    if(task.getResult().exists()){
                        uid = task.getResult().getString("uid");
                    }else{
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}