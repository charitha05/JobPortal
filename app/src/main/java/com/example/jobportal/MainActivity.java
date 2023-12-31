package com.example.jobportal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    private Button register;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        @SuppressLint("CutPasteId") Button register = findViewById(R.id.button6);
        @SuppressLint("CutPasteId") Button login = findViewById(R.id.button5);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
}
