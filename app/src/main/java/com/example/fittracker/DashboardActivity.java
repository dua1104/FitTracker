package com.example.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView caloriesConsumedText, caloriesBurnedText, waterGoalText;
    private Button btnAddMeal, btnAddSport, btnAddWater;

    private double totalCaloriesConsumed = 0;
    private double totalCaloriesBurned = 0;
    private double totalWater = 0; // in liters

    private static final int ADD_MEAL_REQUEST = 100;
    private static final int ADD_SPORT_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashbord);

        caloriesConsumedText = findViewById(R.id.caloriesConsumed);
        caloriesBurnedText = findViewById(R.id.caloriesBurned);
        waterGoalText = findViewById(R.id.waterGoal);

        btnAddMeal = findViewById(R.id.btnAddMeal);
        btnAddSport = findViewById(R.id.btnAddSport);
        btnAddWater = findViewById(R.id.btnAddWater);

        // Add Meal button
        btnAddMeal.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ActivityMeal.class);
            startActivityForResult(intent, ADD_MEAL_REQUEST);
        });

        // Add Sport button
        btnAddSport.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ActivitySport.class);
            startActivityForResult(intent, ADD_SPORT_REQUEST);
        });

        // Add Water button (adds 0.2L)
        btnAddWater.setOnClickListener(v -> {
            totalWater += 0.2;
            updateWaterText();
        });

        updateCaloriesText();
        updateWaterText();
    }

    private void updateCaloriesText() {
        caloriesConsumedText.setText((int) totalCaloriesConsumed + " cal");
        caloriesBurnedText.setText((int) totalCaloriesBurned + " cal");
    }

    private void updateWaterText() {
        waterGoalText.setText(String.format("%.1f L/2L", totalWater));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_MEAL_REQUEST) {
                double mealCalories = data.getDoubleExtra("mealCalories", 0);
                totalCaloriesConsumed += mealCalories;
            } else if (requestCode == ADD_SPORT_REQUEST) {
                double burnedCalories = data.getDoubleExtra("caloriesBurned", 0);
                totalCaloriesBurned += burnedCalories;
            }
            updateCaloriesText();
        }
    }
}
