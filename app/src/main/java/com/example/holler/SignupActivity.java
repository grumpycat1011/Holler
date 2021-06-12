package com.example.holler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText Rname, Ruserid, Remail, Rpass, Rconpass;
    private Button signup;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Rname = findViewById(R.id.Rname);
        Ruserid = findViewById(R.id.Ruserid);
        Remail = findViewById(R.id.Remail);
        Rpass = findViewById(R.id.Rpass);
        Rconpass= findViewById(R.id.Rconpass);
        signup = findViewById(R.id.SignupB);
        fAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = Rname.getText().toString();
                String userid = Ruserid.getText().toString();
                String useremail = Remail.getText().toString();
                String password = Rpass.getText().toString();
                String confirmpass = Rconpass.getText().toString();

                if (fullname.isEmpty()){
                    Rname.setError("Name is a mandatory field!");
                }
                if (userid.isEmpty()){
                    Ruserid.setError("Username is a mandatory field!");
                }
                if (useremail.isEmpty()){
                    Remail.setError("Email is a mandatory field!");
                }
                if (password.isEmpty()){//I am aware of the typo here
                    Rpass.setError("Password is a mandatory field!");
                }
                if (confirmpass.isEmpty()){
                    Rconpass.setError("Confirm password is a mandatory field!");
                }
                if(!password.equals(confirmpass)){
                    Rconpass.setError("The passwords do not match");
                }
                fAuth.createUserWithEmailAndPassword(useremail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));//this will again need to be changed when we implement Preference picker
                        Toast.makeText(SignupActivity.this, "Great success", Toast.LENGTH_SHORT).show();//I did this as a meme from borat change this later
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });
    }
}