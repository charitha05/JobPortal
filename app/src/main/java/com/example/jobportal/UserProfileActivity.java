package com.example.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class UserProfileActivity extends AppCompatActivity {
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView dobTextView;
    private TextView semesterTextView;
    private TextView cgpaTextView;
    private TextView passOutYearTextView;
    private TextView addressTextView;
    private TextView interestTextView;
    private TextView typeTextView;
    private TextView usernameTextView;
    private Button EmployeeButton;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameTextView = findViewById(R.id.profileName);
        emailTextView = findViewById(R.id.profileEmail);
        dobTextView = findViewById(R.id.profileDOB);
        semesterTextView = findViewById(R.id.profileSemester);
        cgpaTextView = findViewById(R.id.profileCGPA);
        passOutYearTextView = findViewById(R.id.profilePassOutYear);
        addressTextView = findViewById(R.id.profileAddress);
        interestTextView = findViewById(R.id.profileInterest);
        typeTextView = findViewById(R.id.profileType);
        usernameTextView = findViewById(R.id.profileUsername);
        EmployeeButton=findViewById(R.id.backToEmployeeButton);
        EmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the login activity
                Intent intent = new Intent(UserProfileActivity.this, Employee.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it when pressing the back button
            }
        });
        // Retrieve the user's email from the Intent
        String userEmail = getIntent().getStringExtra("userEmail");

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Query the database for the user with matching email
        databaseReference.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the user data
                    String fullName = snapshot.child("fullName").getValue(String.class);
                    String dob = snapshot.child("dob").getValue(String.class);
                    String currentSemester = snapshot.child("currentSemester").getValue(String.class);
                    String cgpa = snapshot.child("cgpa").getValue(String.class);
                    String passOutYear = snapshot.child("passOutYear").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String interest = snapshot.child("interest").getValue(String.class);
                    String type = snapshot.child("type").getValue(String.class);
                    String userName = snapshot.child("userName").getValue(String.class);

                    // Update the TextViews with user details
                    if (fullName != null) {
                        nameTextView.setText("Full Name: " + fullName);
                    }
                    if (dob != null) {
                        dobTextView.setText("Date of Birth: " + dob);
                    }
                    if (currentSemester != null) {
                        semesterTextView.setText("Current Semester: " + currentSemester);
                    }
                    if (cgpa != null) {
                        cgpaTextView.setText("CGPA: " + cgpa);
                    }
                    if (passOutYear != null) {
                        passOutYearTextView.setText("Pass Out Year: " + passOutYear);
                    }
                    if (address != null) {
                        addressTextView.setText("Address: " + address);
                    }
                    if (interest != null) {
                        interestTextView.setText("Interested for Placement: " + interest);
                    }
                    if (type != null) {
                        typeTextView.setText("Type: " + type);
                    }
                    if (userName != null) {
                        usernameTextView.setText("Username: " + userName);
                    }
                    if (userEmail != null) {
                        emailTextView.setText("Email: " + userEmail);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                 Toast.makeText(UserProfileActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
