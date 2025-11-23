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

public class ActivityMeal extends AppCompatActivity {

    private EditText etFoodName, etQuantity;
    private Spinner spinnerMealType;
    private Button btnCancel, btnAddMeal;
    private FoodDataBase db;
    private ArrayList<String> mealTypes;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeal);

        etFoodName = findViewById(R.id.foodNameEditText);
        etQuantity = findViewById(R.id.quantityEditText);
        spinnerMealType = findViewById(R.id.mealTypeSpinner);
        btnCancel = findViewById(R.id.cancelButton);
        btnAddMeal = findViewById(R.id.addMealButton);

        db = new FoodDataBase(this);

        // Populate meal types spinner
        mealTypes = new ArrayList<>();
        mealTypes.add("Breakfast");
        mealTypes.add("Lunch");
        mealTypes.add("Dinner");
        mealTypes.add("Snack");

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                mealTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMealType.setAdapter(adapter);

        btnAddMeal.setOnClickListener(v -> addMeal());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void addMeal() {
        String foodName = etFoodName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String mealType = spinnerMealType.getSelectedItem().toString();

        if (foodName.isEmpty() || quantityStr.isEmpty()) {
            showMessage("Veuillez remplir tous les champs");
            return;
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
            if (quantity <= 0) {
                showMessage("Quantité invalide");
                return;
            }
        } catch (NumberFormatException e) {
            showMessage("Quantité invalide");
            return;
        }

        // Get calories per 100g from database
        double caloriesPer100g = db.getCaloriesByName(foodName);

        if (caloriesPer100g == 0) { // food not found
            showMessage("Food not found");
            return;
        }

        double totalCalories = (caloriesPer100g * quantity) / 100.0;

        // Save meal in database
        boolean success = db.addMeal(foodName, quantity, mealType);
        if (success) {
            // Show info in toast
            showMessage(foodName + ": " + quantity + "g = " + totalCalories + " cal");

            // Pass calories back to Dashboard
            Intent resultIntent = new Intent();
            resultIntent.putExtra("mealCalories", totalCalories);
            setResult(RESULT_OK, resultIntent);
            finish(); // go back to Dashboard
        } else {
            showMessage("Erreur lors de l'ajout du repas");
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
