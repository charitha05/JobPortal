package com.example.jobportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText lastName;
    private EditText dob;
    private EditText currentSemester;
    private EditText cgpa;
    private EditText passOutYear;
    private EditText address;
    private RadioGroup interestRadioGroup;
    private RadioGroup typeRadioGroup;
    private EditText username;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullName = findViewById(R.id.editText_fullname);
        lastName = findViewById(R.id.editText_lastname);
        dob = findViewById(R.id.editText_dob);
        currentSemester = findViewById(R.id.editText_semester);
        cgpa = findViewById(R.id.editText_cgpa);
        passOutYear = findViewById(R.id.editText_pass_out_year);
        address = findViewById(R.id.editText_Address);
        interestRadioGroup = findViewById(R.id.radioGroup_interest);
        typeRadioGroup = findViewById(R.id.radioGroup_type);
        username = findViewById(R.id.editText_username);
        email = findViewById(R.id.editText_Email);
        password = findViewById(R.id.editText_password);

        mAuth = FirebaseAuth.getInstance();
        // Set up the DOB field to show a date picker when clicked
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Button register = findViewById(R.id.button2);
        TextView text = findViewById(R.id.textView2);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capture values from the fields
                String txt_fullName = fullName.getText().toString();
                String txt_lastName = lastName.getText().toString();
                String txt_dob = dob.getText().toString();
                int txt_currentSemester = Integer.parseInt(currentSemester.getText().toString());
                double txt_cgpa = Double.parseDouble(cgpa.getText().toString());
                int txt_passOutYear = Integer.parseInt(passOutYear.getText().toString());
                String txt_address = address.getText().toString();
                int selectedInterest = interestRadioGroup.getCheckedRadioButtonId();
                String txt_interest = (selectedInterest == R.id.radioButton_yes) ? "Yes" : "No";
                int selectedType = typeRadioGroup.getCheckedRadioButtonId();
                String txt_type = "";
                if (selectedType == R.id.radioButton_internship) {
                    txt_type = "Internship";
                } else if (selectedType == R.id.radioButton_fulltime) {
                    txt_type = "Full-time";
                } else if (selectedType == R.id.radioButton_both) {
                    txt_type = "Both";
                }
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegistrationActivity.this, "Complete the credentials", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password is too short!", Toast.LENGTH_SHORT).show();
                } else {
                    // You can use the captured data as needed
                    // For example, you can store it in a database or Firebase
                    registerUser(txt_email, txt_password, txt_fullName,
                            txt_lastName, txt_dob,txt_currentSemester,txt_cgpa,
                            txt_passOutYear, txt_address,txt_interest, txt_type,
                            txt_username);
                }
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date (e.g., update the EditText field)
                EditText dobEditText = findViewById(R.id.editText_dob);
                String dob = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                dobEditText.setText(dob);
            }
        }, 2023, 10, 16); // Default date shown when the dialog opens (you can adjust it)

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()); // Set a maximum date (optional)
        datePickerDialog.show();
    }

    private void registerUser(String email, String password, String fullName, String lastName, String dob, int currentSemester, double cgpa, int passOutYear, String address, String interest, String type, String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // You can navigate to another activity here if needed

                            // Now, save the user data to Firebase Database
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

                            UserData userData = new UserData(fullName, lastName, dob, currentSemester, cgpa, passOutYear, address, interest, type, username,email);

                            databaseReference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // User data saved to Firebase Database
                                        Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegistrationActivity.this, Employee.class));
                                        finish();
                                    } else {
                                        // Handle the error
                                    }
                                }
                            });
                        } else {
                            // Check if the error is due to an existing email
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistrationActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
