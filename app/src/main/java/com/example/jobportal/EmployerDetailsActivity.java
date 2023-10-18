package com.example.jobportal;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class EmployerDetailsActivity extends AppCompatActivity {

    private EditText companyNameEditText;
    private EditText yearOfBatchEditText;
    private EditText roleEditText;
    private EditText ctcEditText;
    private EditText applyByDateEditText;
    private Spinner typeSpinner;
    private RadioGroup statusRadioGroup;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_company);

        companyNameEditText = findViewById(R.id.editTextCompanyName);
        yearOfBatchEditText = findViewById(R.id.editTextYearOfBatch);
        roleEditText = findViewById(R.id.editTextRole);
        ctcEditText = findViewById(R.id.editTextCTC);
        applyByDateEditText = findViewById(R.id.editTextApplyByDate);
        applyByDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        // Define an array of options
        String[] options = {"Full-time", "Internship", "Both"};

        // Create an ArrayAdapter to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Find the spinner view by its ID
        Spinner spinner = findViewById(R.id.spinnerType);

        // Set the adapter to the spinner
        spinner.setAdapter(adapter);

        statusRadioGroup = findViewById(R.id.radioGroupStatus);
        databaseReference = FirebaseDatabase.getInstance().getReference("companyDetails");

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmployerDetails();
            }
        });
    }

    public void showDatePickerDialog(View view) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Handle the selected date (e.g., update the EditText field)
                String selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                applyByDateEditText.setText(selectedDate);
            }
        }, currentYear, currentMonth, currentDay); // Set the current date as the default date

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set a minimum date (today)
        datePickerDialog.show();
    }


    private void saveEmployerDetails() {
        String companyName = companyNameEditText.getText().toString();
        int yearOfBatch = Integer.parseInt(yearOfBatchEditText.getText().toString());
        String role = roleEditText.getText().toString();
        double ctc = Double.parseDouble(ctcEditText.getText().toString());
        String applyByDate = applyByDateEditText.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();

        String status = "";
        int selectedStatus = statusRadioGroup.getCheckedRadioButtonId();
        if (selectedStatus == R.id.radioButtonInProgress) {
            status = "In Progress";
        } else if (selectedStatus == R.id.radioButtonCompleted) {
            status = "Completed";
        }

        // Create a CompanyDetails object to store the data
        CompanyDetails companyDetails = new CompanyDetails(companyName, yearOfBatch, role, ctc, applyByDate, type, status);

        // Push the data to Firebase Database
        String companyId = databaseReference.push().getKey();

            databaseReference.child(companyId).setValue(companyDetails)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Data was successfully saved
                            Toast.makeText(EmployerDetailsActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EmployerDetailsActivity.this, Choose.class));
                            finish();
                        } else {
                            // Data failed to save, handle the error (e.g., show an error message)
                            // Log the error message
                            Toast.makeText(EmployerDetailsActivity.this, "Data not saved", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

}
