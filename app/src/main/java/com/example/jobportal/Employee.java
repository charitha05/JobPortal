package com.example.jobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employee extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth mAuth;
    private Button myProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        nameTextView = findViewById(R.id.textView);
        emailTextView = findViewById(R.id.textView2);
        myProfileButton = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        // Check if a user is currently logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If a user is logged in, update the name and email TextViews
            String userName = currentUser.getDisplayName();
            String userEmail = currentUser.getEmail();

            nameTextView.setText(userName);
            emailTextView.setText(userEmail);
        }
        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the log out action (e.g., sign out from Firebase)
                FirebaseAuth.getInstance().signOut();

                // Navigate back to the login activity
                Intent intent = new Intent(Employee.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it when pressing the back button
            }
        });
        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    String currentUserEmail = currentUser.getEmail();

                    Intent intent = new Intent(Employee.this, UserProfileActivity.class);
                    intent.putExtra("userEmail", currentUserEmail);
                    startActivity(intent);
                } else {
                    // Handle the case where the user is not logged in
                    // You may want to redirect the user to the login screen
                }
            }
        });

    }
}
