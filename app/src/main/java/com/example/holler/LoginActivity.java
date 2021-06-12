package com.example.holler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText Lemail, Lpassword;
    private Button Login, gotosignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Lemail = findViewById(R.id.Lemail);
        Lpassword = findViewById(R.id.Lpassword);
        Login = findViewById(R.id.Login);
        gotosignup = findViewById(R.id.button2);

        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
    }
}