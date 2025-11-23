package com.example.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivitySport extends AppCompatActivity {

    private Spinner sportTypeSpinner;
    private EditText etDuration;
    private Button btnAddSport, btnCancel;
    private ArrayList<String> sports;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsport);

        sportTypeSpinner = findViewById(R.id.sportTypeSpinner);
        etDuration = findViewById(R.id.duration);
        btnAddSport = findViewById(R.id.addSportButton);
        btnCancel = findViewById(R.id.cancelButton);

        // List of sports
        sports = new ArrayList<>();
        sports.add("Running");
        sports.add("Cycling");
        sports.add("Swimming");
        sports.add("Walking");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sports);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportTypeSpinner.setAdapter(adapter);

        btnAddSport.setOnClickListener(v -> addSport());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void addSport() {
        String sportName = sportTypeSpinner.getSelectedItem().toString();
        String durationStr = etDuration.getText().toString().trim();

        if (durationStr.isEmpty()) {
            showMessage("Veuillez entrer la durée");
            return;
        }

        double duration;
        try {
            duration = Double.parseDouble(durationStr);
            if (duration <= 0) {
                showMessage("Durée invalide");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Durée invalide");
            return;
        }

        // Simple calories per minute (can be adjusted)
        double caloriesPerMinute;
        switch (sportName.toLowerCase()) {
            case "running": caloriesPerMinute = 10; break;
            case "cycling": caloriesPerMinute = 8; break;
            case "swimming": caloriesPerMinute = 11; break;
            case "walking": caloriesPerMinute = 5; break;
            default: caloriesPerMinute = 6; break;
        }

        double caloriesBurned = duration * caloriesPerMinute;

        // Return the result to DashboardActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("caloriesBurned", caloriesBurned);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
